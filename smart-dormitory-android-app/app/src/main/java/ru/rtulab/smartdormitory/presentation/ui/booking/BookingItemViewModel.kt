package ru.rtulab.smartdormitory.presentation.ui.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.rtulab.smartdormitory.common.Resource
import ru.rtulab.smartdormitory.data.remote.api.booking.models.BookingDto
import ru.rtulab.smartdormitory.data.repository.BookingRepository
import javax.inject.Inject

@HiltViewModel
class BookingItemViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val bookingRepository: BookingRepository,

    ):ViewModel() {



    private var _bookingResourceFlow = MutableStateFlow<Resource<BookingDto>>(Resource.Loading)
    val bookingResourceFlow = _bookingResourceFlow.asStateFlow()

    fun fetchBookingDetails(bookingId:String) = viewModelScope.launch(Dispatchers.IO){
        _bookingResourceFlow.value = bookingRepository.fetchBookingDetails(bookingId)
    }

    fun cancel(bookingId: String) = viewModelScope.launch(Dispatchers.IO) {
        bookingRepository.cancel(bookingId)
    }

}