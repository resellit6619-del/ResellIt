package uk.ac.tees.mad.resellit.ui.setting

data class SettingUiState(
    val isLoading : Boolean = false ,
    val error : String? = null ,
    val success : Boolean = false ,
    val isDialogOpen : Boolean = false ,
    val navigateToLogin: Boolean = false ,
    val isRefreshing : Boolean = false ,
    val profile : String = ""
)