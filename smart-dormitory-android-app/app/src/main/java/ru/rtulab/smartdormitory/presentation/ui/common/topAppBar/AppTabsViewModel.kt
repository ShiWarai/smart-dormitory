package ru.rtulab.smartdormitory.presentation.ui.common.topAppBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.rtulab.smartdormitory.presentation.navigation.AppTab
import javax.inject.Inject


@HiltViewModel
class AppTabsViewModel @Inject constructor(
    //authStateStorage: AuthStateStorage
) : ViewModel() {
    // private val userClaimsFlow = authStateStorage.userClaimsFlow

    private val _statePage = MutableStateFlow(1)
    val statePage = _statePage.asStateFlow()

    private val _appTabs = MutableStateFlow(AppTab.all)
    val appTabs = _appTabs.asStateFlow()


    init {
        viewModelScope.launch {
            var from = 0
            val appTabsAccess = allAppTabsAccess()
            _appTabs.emit(AppTab.all.toList())
        }
    }


    fun allAppTabsAccess(): List<AppTab> {
        return _appTabs.value.filter { it.accessible }
    }
}
