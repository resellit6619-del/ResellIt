package uk.ac.tees.mad.resellit.ui.home.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import uk.ac.tees.mad.resellit.ui.theme.Dimens


@Composable
fun HomeTopBar(modifier: Modifier ,
               onRefreshClick : () -> Unit ,
               isRefreshing : Boolean){

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing)
        ),
        label = ""
    )

    Row(
        modifier = modifier.padding(horizontal = Dimens.Small) ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "ResellIt" ,
            style = MaterialTheme.typography.titleLarge ,
            color = MaterialTheme.colorScheme.onBackground ,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onRefreshClick ,
           modifier = Modifier.padding(end = Dimens.Small)) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "refresh" ,
                modifier = Modifier.rotate(if (isRefreshing) rotation else 0f)
            )
        }
    }
}