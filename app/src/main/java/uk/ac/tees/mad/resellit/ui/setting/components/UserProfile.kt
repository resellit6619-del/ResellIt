package uk.ac.tees.mad.resellit.ui.setting.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.resellit.ui.theme.AppShapes
import uk.ac.tees.mad.resellit.ui.theme.Dimens


@Composable
fun UserProfileCard(modifier: Modifier = Modifier ,
                    profile : String){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(Dimens.ScreenHorizontalPadding) ,
        shape = AppShapes.CardShape ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardElevation
        )){
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = Dimens.ScreenHorizontalPadding) ,
            verticalAlignment = Alignment.CenterVertically ,){

            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "user"
            )
            Spacer(
                modifier = Modifier.width(Dimens.ExtraSmall)
            )
            Text(
                text = profile ,
            )
        }
    }
}