package uk.ac.tees.mad.resellit.ui.setting.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun LogoutButton(modifier: Modifier = Modifier ,
                 onLogoutClick:()-> Unit){
    OutlinedButton(
        onClick = onLogoutClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ScreenHorizontalPadding , vertical = Dimens.bottomBarHeight+Dimens.Small)
    ) {
        Text(
            text = "Logout" ,
            color = MaterialTheme.colorScheme.primary
        )
    }
}