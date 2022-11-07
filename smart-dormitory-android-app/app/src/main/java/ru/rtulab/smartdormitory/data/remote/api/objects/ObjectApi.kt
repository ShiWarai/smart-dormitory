package ru.rtulab.smartdormitory.data.remote.api.objects

import retrofit2.http.GET
import retrofit2.http.Path
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectDto

interface ObjectApi {
    @GET("/objects/")
    suspend fun getAll():List<ObjectDto>

    @GET("/objects/{id}")
    suspend fun getOne(
        @Path("id") objectId: String
    ):ObjectDto

}