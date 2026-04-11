package uk.ac.tees.mad.resellit.ui.post_screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostListingButton(
    onClick: () -> Unit,
    canPost: Boolean ,
    isLoading : Boolean ,
) {

    Button(
        onClick = onClick,
        enabled = canPost,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        when{
           isLoading->{
               CircularProgressIndicator(
                   modifier = Modifier.size(24.dp)
               )
           }
            else->{
               Text(
                   text = "Post"
               )
           }
        }
    }
}