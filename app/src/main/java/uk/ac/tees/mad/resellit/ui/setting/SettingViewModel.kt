package uk.ac.tees.mad.resellit.ui.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.domain.repository.AuthRepository
import uk.ac.tees.mad.resellit.domain.repository.ListingRepository

class SettingViewModel (application: Application) :
AndroidViewModel(application){

    private val authRepository : AuthRepository =
        (application as ResellItApp).container.authRepository

    private val listingRepository : ListingRepository =
        (application as ResellItApp).container.listingRepository
    private val _settingUiState = MutableStateFlow(SettingUiState())
    val settingUiState = _settingUiState.asStateFlow()

    init {
        fetchProfile()
    }

    fun onDialogToggle(){
        _settingUiState.update {
            it.copy(
                isDialogOpen = !it.isDialogOpen
            )
        }
    }

    private fun fetchProfile(){
        viewModelScope.launch {
            val profile = authRepository.fetchEmail()

            _settingUiState.update {
                it.copy(
                    profile = profile
                )
            }
        }
    }


    fun onClearDraft(){

        _settingUiState.update {
            it.copy(isLoading = true ,
                isRefreshing = true)
        }

        viewModelScope.launch {
            listingRepository
                .deleteAllListing()
                .onSuccess {
                    _settingUiState.update {
                        it.copy(
                            isLoading = false ,
                            error = null ,
                            success = true ,
                            isRefreshing = false
                        )
                    }
                }
                .onFailure {error->
                    _settingUiState.update {
                        it.copy(
                            isLoading = false ,
                            error = error.message ,
                            success = false ,
                            isRefreshing = false
                        )
                    }
                }
        }
    }

    fun onLogoutClick(){

        _settingUiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            authRepository
                .logout()
                .onSuccess {
                    Log.d("Setting", "Logout Success")
                    listingRepository
                        .deleteOnLogout()
                        .onSuccess {
                            Log.d("Setting", "delete success")
                            _settingUiState.update {
                                it.copy(
                                    isLoading = false ,
                                    error = null ,
                                    navigateToLogin = true
                                )
                            }
                        }
                        .onFailure {error->
                            Log.d("Setting", error.message.toString())
                            _settingUiState.update {
                                it.copy(
                                    isLoading = false ,
                                    error = error.message ,
                                    navigateToLogin = false
                                )
                            }
                        }
                }
                .onFailure {
                    _settingUiState.update {
                        it.copy(
                            isLoading = false ,
                            error = it.error ,
                            navigateToLogin = false
                        )
                    }
                }
        }
    }

}