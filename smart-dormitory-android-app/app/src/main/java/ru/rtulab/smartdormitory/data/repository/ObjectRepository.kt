package ru.rtulab.smartdormitory.data.repository

import ru.rtulab.smartdormitory.common.ResponseHandler
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectApi
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectTypeApi
import javax.inject.Inject

class ObjectRepository @Inject constructor(
    private val objectApi: ObjectApi,
    private val objectTypeApi: ObjectTypeApi,

    private val handler: ResponseHandler
) {
    suspend fun fetchAllObjects() = handler {
        objectApi.getAll()
    }
    suspend fun fetchAllObjectTypes() = handler {
        objectTypeApi.getAllTypes()
    }
    suspend fun fetchAllObjectRooms() = handler {
        objectTypeApi.getAllRooms()
    }

    suspend fun fetchObjectDetails(objectId: String) = handler {
        objectApi.getOne(objectId)
    }
}