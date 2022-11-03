package ru.rtulab.smartdormitory.presentation.ui.common.topAppBar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.AppTab
import javax.inject.Inject

@HiltViewModel
class AppBarViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val defaultTab = AppTab.Home
    private val _currentScreen = MutableStateFlow(savedStateHandle["currentScreen"] ?: defaultTab.asScreen())
    val currentScreen: StateFlow<AppScreen> = _currentScreen


    private val _currentTab = MutableStateFlow<AppTab>(defaultTab)
    val currentTab: StateFlow<AppTab> = _currentTab

    private val _currentNavHost: MutableStateFlow<NavHostController?> = MutableStateFlow(null)
    val currentNavHost: StateFlow<NavHostController?> = _currentNavHost

    fun onChangeTab(appTab: AppTab) {
        _currentTab.value = appTab

    }
    fun onNavigate(screen: AppScreen, navHostController: NavHostController? = null) {
        _currentScreen.value = screen
        if (navHostController != null)
            _currentNavHost.value = navHostController
        savedStateHandle.set("currentScreen", currentScreen.value)
    }

    fun setCurrentTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // If a deep link is opened from a killed state, nav host's back stack does not yet exist.
    // If we want to be able to use the app normally through a deep link, we need to
    // explicitly recreate the back stack after "Go back" interaction.
    fun handleDeepLinkPop() {
        when (currentScreen.value) {
            is AppScreen.ObjectDetails -> currentNavHost.value?.navigate(AppScreen.ObjectDetails.route)
        }
    }

}