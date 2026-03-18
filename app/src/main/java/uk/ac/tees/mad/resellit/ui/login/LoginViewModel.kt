package uk.ac.tees.mad.resellit.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.domain.repository.AuthRepository

class LoginViewModel (application: Application)
    : AndroidViewModel(application) {
        private val auth: AuthRepository =
            (application as ResellItApp).container.authRepository
        private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()


    /**
     * utility function
     */

    fun onEmailChange(email : String){
        _loginUiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun onPasswordChange(password : String){
        _loginUiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun onVisibilityChange(isPasswordVisible : Boolean){
        _loginUiState.update {
            it.copy(
                isPasswordVisible = isPasswordVisible
            )
        }
    }

    /**
     * function login user
     */
    fun onLoginClick(){
        val state = _loginUiState.value
        viewModelScope.launch {
            _loginUiState.update {
                it.copy(isLoading = true)
            }
            auth
                .loginUser(
                email = state.email ,
                password = state.password
                )
                .onSuccess {
                    _loginUiState.update {
                        it.copy(
                            navigateToHome = true ,
                            isLoading = false ,
                            error = null
                        )
                    }
                }
                .onFailure { error->
                    _loginUiState.update {
                        it.copy(
                            isLoading = false ,
                            error = error.message
                        )
                    }
                }
        }
    }
}