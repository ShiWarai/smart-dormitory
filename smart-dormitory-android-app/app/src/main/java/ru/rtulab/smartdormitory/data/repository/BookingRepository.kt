package ru.rtulab.smartdormitory.data.repository

import ru.rtulab.smartdormitory.common.ResponseHandler
import ru.rtulab.smartdormitory.data.remote.api.booking.BookingApi
import ru.rtulab.smartdormitory.data.remote.api.booking.models.RequestBookingCreate
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val bookingApi: BookingApi,
    private val handler: ResponseHandler
) {
    suspend fun fetchAllBookings() = handler {
        bookingApi.getAll()

    }

    suspend fun fetchBookingDetails(bookingId: String) = handler {
        bookingApi.getOne(bookingId)

    }

    suspend fun createBook(booking: RequestBookingCreate) = handler {
        bookingApi.createBook(booking)

    }

    suspend fun cancel(bookingId: String) {
        bookingApi.cancel(bookingId)
    }
}
