package uk.ac.tees.mad.resellit.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.ac.tees.mad.resellit.ResellItApp
import uk.ac.tees.mad.resellit.preference.PreferenceManager

class SplashViewModel (application: Application) :
AndroidViewModel(application){
    private val preferenceManager : PreferenceManager =
        (application as ResellItApp).container.preferenceManager

    private val _splashUiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val splashUiState = _splashUiState.asStateFlow()

    init {
        resolveSplash()
    }

    fun resolveSplash(){
        when{
            preferenceManager.isLoggedIn() -> {
                _splashUiState.value = SplashUiState.NavigateToHome
            }
            else -> {
                _splashUiState.value = SplashUiState.NavigateToLogin
            }
        }
    }
}