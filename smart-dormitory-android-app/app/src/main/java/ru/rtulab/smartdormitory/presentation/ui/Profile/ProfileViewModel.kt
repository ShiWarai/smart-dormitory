package ru.rtulab.smartdormitory.presentation.ui.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.rtulab.smartdormitory.common.Resource
import ru.rtulab.smartdormitory.common.emitInIO
import ru.rtulab.smartdormitory.common.persistence.AuthStateStorage
import ru.rtulab.smartdormitory.data.remote.api.profile.models.ProfileDto
import ru.rtulab.smartdormitory.data.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepository,
    private val authStateStorage: AuthStateStorage,
) : ViewModel() {

    private var _profileResourceFlow = MutableStateFlow<Resource<ProfileDto>>(Resource.Loading)
    val profileResourceFlow = _profileResourceFlow.asStateFlow().also {
        fetchMe()
    }

    fun fetchMe() = _profileResourceFlow.emitInIO(viewModelScope) {
        var resource: Resource<ProfileDto> = Resource.Loading
        profileRepo.fetchMe(authStateStorage.user!!).handle(
            onSuccess = { me ->
                resource = Resource.Success(me)
            },
            onError = {
                resource = Resource.Error(it)
            }
        )
        resource
    }

}