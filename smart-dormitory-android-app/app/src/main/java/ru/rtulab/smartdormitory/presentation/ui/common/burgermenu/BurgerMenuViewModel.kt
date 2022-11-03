package ru.rtulab.smartdormitory.presentation.ui.common.burgermenu

import androidx.compose.material.DrawerState
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.rtulab.smartdormitory.presentation.navigation.AppTab
import javax.inject.Inject

@HiltViewModel
class BurgerMenuViewModel @Inject constructor(

):ViewModel()
{
    var bottomSheetState = false//hide

    fun reverseState() {
        bottomSheetState  = !bottomSheetState
    }
}