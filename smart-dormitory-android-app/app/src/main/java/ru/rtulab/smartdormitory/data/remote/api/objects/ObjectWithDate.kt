package ru.rtulab.smartdormitory.data.remote.api.objects

import kotlinx.serialization.Serializable

@Serializable
data class ObjectWithDate(
    val id: String,
    val objectId: String,
    val name: String,
    val status: String,
    val type: String,
    val room: String,
    val startTime: String,
    val endTime: String,
)
