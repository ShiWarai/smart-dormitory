package ru.rtulab.smartdormitory.data.remote.api.report.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestCreateReport(
    val description: String,
    val pictures: List<String>,
    val objectId: Int,
    val residentId: Int
)
