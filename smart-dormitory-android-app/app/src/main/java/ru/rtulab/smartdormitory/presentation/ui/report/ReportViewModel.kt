package ru.rtulab.smartdormitory.presentation.ui.report

import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.rtulab.smartdormitory.data.remote.api.report.models.RequestCreateReport
import ru.rtulab.smartdormitory.data.repository.ReportRepository
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
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
    private suspend fun showSnackbar(text: String) {
        snackbarHostState.showSnackbar(text)
    }

}