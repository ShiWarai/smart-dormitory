package ru.rtulab.smartdormitory.presentation.ui.objects

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.rtulab.smartdormitory.data.repository.ObjectRepository
import javax.inject.Inject

@HiltViewModel
class ObjectItemViewModel @Inject constructor(
    private val objectRepository: ObjectRepository,
    private val savedState: SavedStateHandle

):ViewModel(){

    /*private var _objectResourceFlow = MutableStateFlow<Resource<ObjectDto>>(Resource.Loading)
    val objectResourceFlow = _objectResourceFlow.asStateFlow().also {
        fetchBookingDetails()
    }*/

    /*fun fetchBookingDetails() = _objectResourceFlow.emitInIO(viewModelScope){
        objectRepository.fetchObjectDetails(objectId)
    }*/


}