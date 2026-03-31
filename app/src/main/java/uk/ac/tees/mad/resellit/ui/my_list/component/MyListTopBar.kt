package uk.ac.tees.mad.resellit.ui.my_list.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.Dimens


@Composable
fun MyListTopBar(
    modifier: Modifier = Modifier
){
    Row (modifier = modifier
        .fillMaxWidth()
        .height(Dimens.topBarHeight)
        .padding(horizontal = Dimens.Small),
        verticalAlignment =Alignment.CenterVertically){
        Text(
            text = "My List" ,
            style = MaterialTheme.typography.titleLarge ,
            color = MaterialTheme.colorScheme.onBackground ,
        )
    }
}