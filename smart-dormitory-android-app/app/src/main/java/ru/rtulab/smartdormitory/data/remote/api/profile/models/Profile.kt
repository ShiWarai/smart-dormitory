package ru.rtulab.smartdormitory.data.remote.api.profile.models

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val imageLink: String,
    val login: String,
    val room: String,
    val role: String,
    val lastName: String,
    val firstName: String
)
