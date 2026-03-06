package uk.ac.tees.mad.resellit.ui.signup

import uk.ac.tees.mad.resellit.util.isValidEmail

data class SignupUiState (
    val isLoading : Boolean = false ,
    val error : String ? = null ,
    val email: String ="" ,
    val password : String = "" ,
    val confirmPassword :String = "" ,
    val isPasswordVisible : Boolean = false ,
    val isConfirmPasswordVisible : Boolean = false ,
    val navigateToHome : Boolean = false
){
    val canSubmit : Boolean
        get() = isValidEmail(email) && password.length >= 8 && password == confirmPassword
}

/**
 * signup ui state is the data class having the state related to the signup screen
 */

