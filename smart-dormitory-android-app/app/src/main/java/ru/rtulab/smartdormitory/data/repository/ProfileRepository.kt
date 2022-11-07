package ru.rtulab.smartdormitory.data.repository

import ru.rtulab.smartdormitory.common.ResponseHandler
import ru.rtulab.smartdormitory.data.remote.api.profile.ProfileApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi,
    private val handler: ResponseHandler
) {

    suspend fun fetchMe(studentId: String) = handler {
        profileApi.getMe(studentId)

    }

    suspend fun fetchById(studentId: String) = handler {
        profileApi.getById(studentId)

    }
}