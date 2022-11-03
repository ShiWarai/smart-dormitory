package ru.rtulab.smartdormitory.presentation.ui.common.bottomsheet

import androidx.compose.material.ExperimentalMaterialApi

@ExperimentalMaterialApi
sealed class AppBottomSheet {
    object Unspecified: AppBottomSheet()
    override fun equals(other: Any?): Boolean {
        return false
    }
}