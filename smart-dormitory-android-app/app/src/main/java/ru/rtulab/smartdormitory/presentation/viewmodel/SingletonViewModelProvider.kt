package ru.rtulab.smartdormitory.presentation.viewmodel

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Returns an existing [ViewModel] or creates a new one in the activity,
 * provided by [LocalActivity].
 *
 * The created [ViewModel] is associated with the current activity and will be retained
 * as long as the owner is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * @return A [ViewModel] that is an instance of the given [VM] type.
 */
@Composable
internal inline fun <reified VM : ViewModel> singletonViewModel(): VM =
    viewModel(
        viewModelStoreOwner = LocalActivity.current
    )

val LocalActivity = compositionLocalOf<ComponentActivity> {
    error("Why in the world would you access an Activity now?")
}