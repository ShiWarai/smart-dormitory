package ru.rtulab.smartdormitory.presentation.ui.common.burgermenu

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BurgerMenuViewModel @Inject constructor(

) : ViewModel() {
    var bottomSheetState = false//hide

    fun reverseState() {
        bottomSheetState = !bottomSheetState
    }
}