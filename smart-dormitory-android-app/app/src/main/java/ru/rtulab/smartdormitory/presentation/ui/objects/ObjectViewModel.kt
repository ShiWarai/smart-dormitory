package ru.rtulab.smartdormitory.presentation.ui.objects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.rtulab.smartdormitory.common.Resource
import ru.rtulab.smartdormitory.common.emitInIO
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithoutDate
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectDto
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectRoom
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectType
import ru.rtulab.smartdormitory.data.repository.ObjectRepository
import javax.inject.Inject

@HiltViewModel
class ObjectViewModel @Inject constructor(
    private val objectRepo: ObjectRepository,
) : ViewModel() {


    private var _objectsResourceFlow = MutableStateFlow<Resource<List<ObjectDto>>>(Resource.Loading)
    val objectsResourceFlow = _objectsResourceFlow.asStateFlow().also {
        fetchAllObjects()
    }

    fun fetchAllObjects() = _objectsResourceFlow.emitInIO(viewModelScope) {
        objectRepo.fetchAllObjects()
    }

    private var _objectTypesResourceFlow =
        MutableStateFlow<Resource<List<ObjectType>>>(Resource.Loading)
    val objectTypesResourceFlow = _objectTypesResourceFlow.asStateFlow().also {
        fetchAllObjectTypes()
    }

    fun fetchAllObjectTypes() = _objectTypesResourceFlow.emitInIO(viewModelScope) {
        objectRepo.fetchAllObjectTypes()
    }

    private var _objectRoomsResourceFlow =
        MutableStateFlow<Resource<List<ObjectRoom>>>(Resource.Loading)
    val objectRoomsResourceFlow = _objectRoomsResourceFlow.asStateFlow().also {
        fetchAllObjectRooms()
    }

    fun fetchAllObjectRooms() = _objectRoomsResourceFlow.emitInIO(viewModelScope) {
        objectRepo.fetchAllObjectRooms()
    }


    fun onRefresh() {
        fetchAllObjects()
        fetchAllObjectTypes()
    }

    private var cachedObjects = emptyList<ObjectWithoutDate>()

    private var _ObjectsFlow = MutableStateFlow(cachedObjects)
    val objectsFlow = _ObjectsFlow.asStateFlow()

    fun onSearch(query: String) {
        _ObjectsFlow.value = cachedObjects.filter { filterSearchResult(it, query) }

    }

    private fun filterSearchResult(obj: ObjectWithoutDate, query: String) = obj.run {
        name.contains(query.trim(), ignoreCase = true) ||
                room.contains(query.trim(), ignoreCase = true) ||
                type.contains(query.trim(), ignoreCase = true) ||
                if (status == 200) "Свободно".contains(
                    query.trim(),
                    ignoreCase = true
                ) else "Занято".contains(query.trim(), ignoreCase = true)

    }

    fun onResourceSuccess(
        objs: List<ObjectDto>,
        objType: List<ObjectType>,
        objRoom: List<ObjectRoom>
    ): List<ObjectWithoutDate> {
        cachedObjects = objs.map {
            it.toObject(objType.find { t -> t.id == it.typeId }!!,
                objRoom.find { t -> t.id == it.roomId }!!
            )
        }
        _ObjectsFlow.value = cachedObjects
        return cachedObjects
    }


}