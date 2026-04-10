package uk.ac.tees.mad.resellit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.resellit.navigation.AppNavHost
import uk.ac.tees.mad.resellit.navigation.NavRoutes
import uk.ac.tees.mad.resellit.ui.login.LoginScreenContent
import uk.ac.tees.mad.resellit.ui.splash.SplashUiState
import uk.ac.tees.mad.resellit.ui.splash.SplashViewModel
import uk.ac.tees.mad.resellit.ui.theme.ResellItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashViewModel : SplashViewModel by viewModels()
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.splashUiState.value is SplashUiState.Loading
        }
        setContent {
            val navController = rememberNavController()
            val startDestination = when(splashViewModel
                .splashUiState
                .collectAsState()
                .value){
                SplashUiState.NavigateToHome -> NavRoutes.Home
                SplashUiState.NavigateToLogin -> NavRoutes.Login
                else -> null
            }
            startDestination?.let {
                ResellItTheme {
                    AppNavHost(
                        startDestination = startDestination ,
                        navController = navController
                    )
                }
            }
        }
    }
}