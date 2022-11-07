package ru.rtulab.smartdormitory.presentation.ui.report

import android.util.Log
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.rtulab.smartdormitory.common.Resource
import ru.rtulab.smartdormitory.common.emitInIO
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithoutDate
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectDto
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectRoom
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectType
import ru.rtulab.smartdormitory.data.remote.api.profile.models.ProfileDto
import ru.rtulab.smartdormitory.data.remote.api.report.models.Report
import ru.rtulab.smartdormitory.data.remote.api.report.models.ReportDto
import ru.rtulab.smartdormitory.data.remote.api.report.models.RequestCreateReport
import ru.rtulab.smartdormitory.data.repository.ObjectRepository
import ru.rtulab.smartdormitory.data.repository.ProfileRepository
import ru.rtulab.smartdormitory.data.repository.ReportRepository
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val profileRepository: ProfileRepository,
    private val objectRepository: ObjectRepository,
):ViewModel() {
    var objectId = MutableStateFlow<Int?>(null);
    var residentId = MutableStateFlow<Int?>(null);
    var pictures:List<String> = emptyList()
    var _description = MutableStateFlow<String>("");
    var description = _description.asStateFlow()

    val snackbarHostState = SnackbarHostState()

    fun createReport() = viewModelScope.launch(Dispatchers.IO){
        if(objectId!=null && residentId!=null) {
            val requestCreateReport = RequestCreateReport(
                _description.value,
                pictures,
                objectId.value!!,
                residentId.value!!
            )
            reportRepository.createReport(requestCreateReport).handle(
                onSuccess = {
                    if(it.isSuccessful)
                    showSnackbar("Успешно отправлено")
                },
                onError = {
                    showSnackbar("Something Wrong")
                }
            )
        }
    }

    val _reportsDtoFlow = MutableStateFlow<Resource<List<Report?>>>(Resource.Loading)
    val reportsDtoFlow = _reportsDtoFlow.asStateFlow().also { fetchAllReports() }
    private fun fetchAllReports() = _reportsDtoFlow.emitInIO(viewModelScope){
        var resource: Resource<List<Report?>> = Resource.Loading

                var listRep = mutableListOf<Pair<ReportDto,Pair<ObjectDto,ProfileDto>>?>()
                reportRepository.getAll().handle(
                    onSuccess = { reportsDto ->
                        Log.d("PAIRReport",reportsDto.toString())

                        var pair:Pair<ReportDto,Pair<ObjectDto,ProfileDto>>? = null

                        reportsDto.forEach { reportDto ->

                            (objectRepository.fetchObjectDetails(reportDto.objectId.toString()) + profileRepository.fetchById(
                                reportDto.residentId.toString()
                            )).handle(
                                onSuccess = { (obj, resident) ->
                                    if(obj!=null && resident!=null) {
                                        Log.d("PAIR", obj.toString() + " " + resident.toString())
                                        pair = reportDto to Pair(
                                            obj as ObjectDto,
                                            resident as ProfileDto
                                        )
                                        listRep.add(pair)

                                    }

                                },
                                onError = {

                                    Log.d("PAIR",it)

                                }

                            )
                        }
                    },
                    onError = {
                        Log.d("PAIRReport",it)

                    }
                )

        val list = listRep.map { pair ->
            val reportDto = pair!!.first
            val obj = pair!!.second.first
            val resident = pair!!.second.second
            var repOne:Report? = null
            (objectRepository.fetchObjectOneType((obj as ObjectDto).typeId.toString()) + objectRepository.fetchObjectOneRoom(
                (obj as ObjectDto).roomId.toString()
            )).handle(
                onSuccess = { (t, r) ->
                    repOne = reportDto.toReport(
                        (obj as ObjectDto).toObject(t as ObjectType, r as ObjectRoom),
                        resident as ProfileDto
                    )

                },
                onError = {

                }
            )
            repOne
        }
        resource = Resource.Success(list)
        resource
    }

    private suspend fun showSnackbar(text: String) {
        snackbarHostState.showSnackbar(text)
    }

    fun onRefresh() {
        fetchAllReports()
    }

}