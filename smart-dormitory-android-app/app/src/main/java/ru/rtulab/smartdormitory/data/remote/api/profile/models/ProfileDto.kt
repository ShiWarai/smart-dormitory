package ru.rtulab.smartdormitory.data.remote.api.profile.models

import kotlinx.serialization.Serializable

@Serializable
class ProfileDto(
    val id: Int,
    val fio: String,
    val birthdate: String,
    val studentId: String,
    val room: Int,
    val role: Int

) {
    fun toProfile() =
        Profile(
            lastName = fio.split(" ")[0],
            firstName = fio.split(" ")[1] ?: "",
            room = "$room",
            role = "$role",
            imageLink = "",
            login = studentId
        )
}
