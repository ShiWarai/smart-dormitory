package ru.rtulab.smartdormitory.data.remote.api.objects

import retrofit2.http.GET
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectDto
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectRoom
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectType

interface ObjectTypeApi {
        @GET("/object_types/")
        suspend fun getAllTypes():List<ObjectType>

        @GET("/rooms/")
        suspend fun getAllRooms():List<ObjectRoom>
}