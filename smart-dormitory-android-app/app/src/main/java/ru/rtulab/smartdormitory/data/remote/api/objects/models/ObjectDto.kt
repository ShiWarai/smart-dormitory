package ru.rtulab.smartdormitory.data.remote.api.objects.models

import kotlinx.serialization.Serializable
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithoutDate

@Serializable
data class ObjectDto(
    val id:Int,
    val name:String,
    val description:String,
    val cloudId:String,
    val available:Boolean,
    val typeId:Int,
    val statusId:Int,
    val roomId:Int,
){
    fun toObject(objType: ObjectType,objRoom:ObjectRoom) = ObjectWithoutDate(
        id =id.toString(),
        name =name,
        type = objType.name,
        status = statusId,
        room = objRoom.name,
        description = description

    )
}
