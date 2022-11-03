package ru.rtulab.smartdormitory.data.remote.api.booking.models

import kotlinx.serialization.Serializable
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithDate
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithoutDate
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectDto
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectType

@Serializable
data class BookingDto(
    val id:Int,
    val reason:String,
    val isCanceled:Boolean,
    val begin:String,
    val end:String,
    val objectId:Int,
    val status:String,
    val resident:Int
){
    fun toBooking(obj: ObjectWithoutDate) = ObjectWithDate(
        id = id.toString(),
        objectId = obj.id,
        startTime = begin,
        endTime = end,
        room = obj.room,
        name = obj.name,
        type = obj.type,
        status = status

    )
}