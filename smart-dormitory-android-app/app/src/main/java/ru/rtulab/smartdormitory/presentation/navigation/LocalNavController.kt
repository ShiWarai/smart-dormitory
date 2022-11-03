package ru.rtulab.smartdormitory.presentation.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    throw IllegalStateException("NavController does not yet exist")
}