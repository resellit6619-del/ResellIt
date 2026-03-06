package uk.ac.tees.mad.resellit.ui.signup

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

class SignupViewModel(application: Application):
    AndroidViewModel(application){
        private val auth : AuthRepository =
            (application as ResellItApp).container.authRepository
        private val _signupUiState = MutableStateFlow(SignupUiState())
    val signupUiState = _signupUiState.asStateFlow()

    /**
     * utility function
     */
    fun onEmailChange(email: String){
        _signupUiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun onPasswordChange(password : String){
        _signupUiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword : String) {
        _signupUiState.update {
            it.copy(
                confirmPassword = confirmPassword
            )
        }
    }

    fun onPasswordVisibilityChange(isPasswordVisible : Boolean){
        _signupUiState.update {
            it.copy(
                isPasswordVisible = isPasswordVisible
            )
        }
    }

    fun onConfirmPasswordVisibilityChange(isPasswordVisible : Boolean){
        _signupUiState.update {
            it.copy(
                isConfirmPasswordVisible = isPasswordVisible
            )
        }
    }

    /**
     * function for registering the user
     */
    fun register(){
        val state = _signupUiState.value
        viewModelScope.launch {
            _signupUiState.update {
                it.copy(isLoading = true)
            }
            auth
                .registerUser(
                    email = state.email ,
                    password = state.password
                ).onSuccess {
                    _signupUiState.update {
                        it.copy(
                            navigateToHome = true ,
                            isLoading = false ,
                            error = null
                        )
                    }
                }
                .onFailure { error->
                    Log.d("SignupViewModel" , "register: ${error.message}")
                    _signupUiState.update {
                        it.copy(
                            isLoading = false ,
                            error = error.message
                        )
                    }
                }
        }
    }
}
