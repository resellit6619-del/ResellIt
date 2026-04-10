package uk.ac.tees.mad.resellit.navigation

sealed class NavRoutes (val route : String){
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("home")
    object Setting : NavRoutes("setting")
    object List : NavRoutes("list")

    object AddEditView : NavRoutes("add")
}