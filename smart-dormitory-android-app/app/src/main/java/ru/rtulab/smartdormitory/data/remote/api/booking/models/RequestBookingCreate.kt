package ru.rtulab.smartdormitory.data.remote.api.booking.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestBookingCreate(
    val objectId:Int,
    val reason:String,
    val begin:String,
    val end:String
)