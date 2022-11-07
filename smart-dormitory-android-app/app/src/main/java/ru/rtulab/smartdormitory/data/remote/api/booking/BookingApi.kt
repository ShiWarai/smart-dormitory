package ru.rtulab.smartdormitory.data.remote.api.booking

import retrofit2.Response
import retrofit2.http.*
import ru.rtulab.smartdormitory.data.remote.api.booking.models.BookingDto
import ru.rtulab.smartdormitory.data.remote.api.booking.models.RequestBookingCreate

interface BookingApi {

    @GET("/reservations/")
    suspend fun getAll():List<BookingDto>

    @GET("/reservations/{bookingId}")
    suspend fun getOne(
        @Path("bookingId") bookingId:String
    ):BookingDto

    @POST("/reservations/")
    suspend fun createBook(
        @Body booking:RequestBookingCreate
    ):Response<Unit>

    @DELETE("/reservations/{bookingId}/cancel")
    suspend fun cancel(
        @Path("bookingId") bookingId: String
    ):Response<Unit>
}