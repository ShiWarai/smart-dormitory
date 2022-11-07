package ru.rtulab.smartdormitory.data.remote.api.profile

import retrofit2.http.GET
import retrofit2.http.Path
import ru.rtulab.smartdormitory.data.remote.api.profile.models.ProfileDto

interface ProfileApi {
    @GET("/residents/{student_id}")
    suspend fun getMe(
        @Path("student_id") student_id: String
    ): ProfileDto

    @GET("/residents/by_id/{student_id}")
    suspend fun getById(
        @Path("student_id") student_id: String
    ): ProfileDto

}