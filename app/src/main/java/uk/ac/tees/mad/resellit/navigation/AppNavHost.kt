package uk.ac.tees.mad.resellit.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import uk.ac.tees.mad.resellit.ui.components.AddListingFab
import uk.ac.tees.mad.resellit.ui.components.BottomNavigationBar
import uk.ac.tees.mad.resellit.ui.home.HomeScreen
import uk.ac.tees.mad.resellit.ui.login.LoginScreen
import uk.ac.tees.mad.resellit.ui.my_list.MyListScreen
import uk.ac.tees.mad.resellit.ui.post_screen.PostScreen
import uk.ac.tees.mad.resellit.ui.setting.SettingScreen
import uk.ac.tees.mad.resellit.ui.signup.SignupScreen
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun AppNavHost(startDestination: NavRoutes,
               navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute == NavRoutes.Home.route ||
                        currentRoute == NavRoutes.List.route ||
                        currentRoute == NavRoutes.Setting.route

    val showFab = currentRoute == NavRoutes.Home.route ||
                currentRoute == NavRoutes.List.route

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            startDestination = startDestination.route,
            navController = navController
        ) {
            composable(NavRoutes.Login.route) {
                LoginScreen(
                    onNavigateToHome = {
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Login.route) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(NavRoutes.Register.route)
                    }
                )
            }
            composable(NavRoutes.Register.route) {
                SignupScreen(
                    onNavigateToLogin = {
                        navController.popBackStack()
                    },
                    onNavigateToHome = {
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Register.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(NavRoutes.Home.route) {
                HomeScreen()
            }
            composable(NavRoutes.List.route){
                MyListScreen()
            }
            composable(NavRoutes.Setting.route){
                SettingScreen(
                    onNavToLogin = {
                        navController.navigate(NavRoutes.Login.route){
                            popUpTo(NavRoutes.Setting.route){
                                inclusive = true
                            }
                        }
                    },
                )
            }
            composable(NavRoutes.AddEditView.route){
                PostScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        if(showBottomBar){
            BottomNavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                currentRoute = currentRoute,
                onSettingClick = {
                    navController.navigate(NavRoutes.Setting.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
                restoreState = true
            }
                },
                onListClick = {
                    navController.navigate(NavRoutes.List.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onHomeClick = {
                    navController.navigate(NavRoutes.Home.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        AnimatedVisibility(
            visible = showFab,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = Dimens.ScreenHorizontalPadding,
                    bottom = Dimens.bottomBarHeight + 16.dp
                )
        ) {
            AddListingFab(
                onClick = {
                    navController.navigate(NavRoutes.AddEditView.route)
                }
            )
        }
    }
}