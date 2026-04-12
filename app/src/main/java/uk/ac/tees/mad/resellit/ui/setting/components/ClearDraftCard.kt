package uk.ac.tees.mad.resellit.ui.setting.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.AppShapes
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun ClearDraftCard(modifier: Modifier = Modifier ,
                   onClick:()-> Unit){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = Dimens.ScreenHorizontalPadding) ,
        shape = AppShapes.CardShape ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardElevation
        )){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenHorizontalPadding) ,
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "clear draft" ,
            )

            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh ,
                    contentDescription = "clear draft"
                )
            }
        }
    }
}