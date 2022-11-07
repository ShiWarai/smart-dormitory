package ru.rtulab.smartdormitory.presentation.ui.booking

import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.rtulab.smartdormitory.common.Resource
import ru.rtulab.smartdormitory.common.emitInIO
import ru.rtulab.smartdormitory.common.persistence.AuthStateStorage
import ru.rtulab.smartdormitory.data.remote.api.booking.models.BookingDto
import ru.rtulab.smartdormitory.data.remote.api.booking.models.RequestBookingCreate
import ru.rtulab.smartdormitory.data.remote.api.objects.ObjectWithDate
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectDto
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectRoom
import ru.rtulab.smartdormitory.data.remote.api.objects.models.ObjectType
import ru.rtulab.smartdormitory.data.repository.BookingRepository
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepo: BookingRepository,
    private val authStateStorage: AuthStateStorage,
) : ViewModel() {
    private var _bookingsResourceFlow =
        MutableStateFlow<Resource<List<BookingDto>>>(Resource.Loading)
    val bookingsResourceFlow = _bookingsResourceFlow.asStateFlow().also {
        fetchAllBooking()
    }

    fun fetchAllBooking() = _bookingsResourceFlow.emitInIO(viewModelScope) {
        bookingRepo.fetchAllBookings()
    }

    fun onRefresh() {
        fetchAllBooking()
    }

    private var cachedObjects = emptyList<ObjectWithDate>()

    private var _bookingsFlow = MutableStateFlow(cachedObjects)
    val bookingsFlow = _bookingsFlow.asStateFlow()

    fun onResourceSuccess(
        objs: List<BookingDto>,
        objdto: List<ObjectDto>,
        objType: List<ObjectType>,
        objRoom: List<ObjectRoom>
    ) {
        cachedObjects = objs.map {
            it.toBooking(objdto.find { obj -> obj.id == it.objectId }!!.run {
                toObject(objType.find { t -> t.id == typeId }!!,
                    objRoom.find { t -> t.id == roomId }!!
                )
            })
        }
        _bookingsFlow.value = cachedObjects
    }

    //for book
    val snackbarHostState = SnackbarHostState()

    var cachedObjId = MutableStateFlow<Int>(-1)
    var beginTime = MutableStateFlow("")

    var endTime = MutableStateFlow("")

    fun createBook() = viewModelScope.launch(Dispatchers.IO) {
        val bookingDto: RequestBookingCreate = RequestBookingCreate(
            objectId = cachedObjId.value,
            reason = "Ответочка от мобилочки",
            begin = beginTime.value,
            end = endTime.value
        )
        bookingRepo.createBook(bookingDto).handle(
            onSuccess = {
                if (it.isSuccessful)
                    showSnackbar("Успешно забронирован")
            },
            onError = {
                showSnackbar("Что то пошло не так...")
            }
        )
    }

    private suspend fun showSnackbar(text: String) {
        snackbarHostState.showSnackbar(text)
    }

}