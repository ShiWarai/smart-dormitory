package ru.rtulab.smartdormitory.data.remote.api.report.models

import kotlinx.serialization.Serializable
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithoutDate
import ru.rtulab.smartdormitory.data.remote.api.profile.models.ProfileDto

@Serializable
data class Report(
    val id: Int,
    val description: String,
    val isDone: Boolean,
    val pictures: List<String>,
    val objectFull: ObjectWithoutDate,
    val resident: ProfileDto
)
