package uk.ac.tees.mad.resellit.ui.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun DetailTopBar(modifier: Modifier = Modifier ,
                 onBackClick:()-> Unit){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Small)
            .height(Dimens.topBarHeight) ,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onBackClick){
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew ,
                contentDescription = "back icon"
            )
        }
        Spacer(modifier = Modifier.width(Dimens.ExtraSmall))
        Text(
           text = "Detail "  ,
            style = MaterialTheme.typography.titleLarge ,
            color = MaterialTheme.colorScheme.onBackground ,
        )
    }
}