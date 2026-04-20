package uk.ac.tees.mad.resellit.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.Dimens


@Composable
fun HomeTopBar(modifier: Modifier){
    Row(
        modifier = modifier.padding(horizontal = Dimens.Small) ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "ResellIt" ,
            style = MaterialTheme.typography.titleLarge ,
            color = MaterialTheme.colorScheme.onBackground ,
        )
    }
}