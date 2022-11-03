package ru.rtulab.smartdormitory.presentation.ui.common.bottomsheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class BottomSheetViewModel @Inject constructor(

) : ViewModel() {
    val bottomSheetState = ModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    private var _currentBottomSheet = MutableStateFlow<AppBottomSheet>(AppBottomSheet.Unspecified)
    val currentBottomSheet = _currentBottomSheet.asStateFlow()

    private var _visibilityAsState = MutableStateFlow(bottomSheetState.isVisible)
    val visibilityAsState = _visibilityAsState.asStateFlow()

    fun show(sheet: AppBottomSheet, scope: CoroutineScope) = scope.launch {
        _currentBottomSheet.value = sheet
        _visibilityAsState.value = true
        bottomSheetState.show()
    }

    fun hide(scope: CoroutineScope) = scope.launch {
        bottomSheetState.hide()
        _visibilityAsState.value = false
    }

}