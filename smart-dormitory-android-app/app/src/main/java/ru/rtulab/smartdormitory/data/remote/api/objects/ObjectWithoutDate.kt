package ru.rtulab.smartdormitory.data.remote.api.objects

import kotlinx.serialization.Serializable

@Serializable
data class ObjectWithoutDate(
    val id: String,
    val name: String,
    val status: Int,
    val type: String,
    val room: String,
    val description: String,
)