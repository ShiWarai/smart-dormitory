package ru.rtulab.smartdormitory.data.remote.api.objects.models

data class ObjectType(
    val id:Int,
    val name:String,

)
data class ObjectRoom(
    val id:Int,
    val position:String,
    val name:String,
    val typeId:Int

)
