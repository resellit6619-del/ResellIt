package uk.ac.tees.mad.resellit.ui.post_screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun PostTopBar(modifier: Modifier = Modifier ,
               onBackClick:()-> Unit){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.topBarHeight)
            .padding(horizontal = Dimens.ScreenHorizontalPadding) ,
        verticalAlignment = Alignment.CenterVertically ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onBackClick) {
            Icon(
                imageVector = Icons.Default.Close ,
                contentDescription = "back" ,
            )
        }
        Text(
            text = "Post Listing" ,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = "Drafts" ,
            color = MaterialTheme.colorScheme.primary
        )
    }
}