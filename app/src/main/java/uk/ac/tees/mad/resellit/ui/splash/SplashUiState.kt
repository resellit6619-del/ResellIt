package uk.ac.tees.mad.resellit.ui.splash
sealed class SplashUiState{
    object Loading : SplashUiState()
    object NavigateToLogin : SplashUiState()
    object NavigateToHome : SplashUiState()
}
