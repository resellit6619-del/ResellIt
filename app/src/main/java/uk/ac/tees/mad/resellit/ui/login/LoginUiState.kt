package uk.ac.tees.mad.resellit.ui.login

import uk.ac.tees.mad.resellit.util.isValidEmail

data class LoginUiState(
    val email : String = "" ,
    val password : String = "" ,
    val isPasswordVisible : Boolean = false ,
    val navigateToHome : Boolean = false ,
    val isLoading : Boolean = false ,
    val error : String? = null
){
    val canSubmit : Boolean
        get() = isValidEmail(email) && password.length >= 8
}


/**
 * this is a login ui state which is associated with login screen
 * login screen will read this ui state and will react accordingly
 **/

/**
 * email - for email string
 * password - for password string
 * isPasswordVisible - for toggling the eye icon
 * val is loading for loader
 */