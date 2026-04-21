package uk.ac.tees.mad.resellit.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.navigation.NavRoutes
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier ,
    currentRoute : String? ,
    onSettingClick:()-> Unit ,
    onListClick:()-> Unit ,
    onHomeClick:()-> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.bottomBarHeight)
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = currentRoute == NavRoutes.Home.route ,
            onClick = onHomeClick
        )

        BottomNavItem(
            icon = Icons.Default.List,
            label = "MyList",
            selected = currentRoute == NavRoutes.List.route ,
            onClick = onListClick
        )

        BottomNavItem(
            icon = Icons.Default.Settings,
            label = "Settings",
            selected = currentRoute == NavRoutes.Setting.route ,
            onClick = onSettingClick
        )
    }
}



@Composable
fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val color = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurface

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable{
                onClick()
            }
        ) {

            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )

        }
    }