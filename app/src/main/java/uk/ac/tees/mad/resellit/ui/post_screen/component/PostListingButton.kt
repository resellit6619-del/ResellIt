package uk.ac.tees.mad.resellit.ui.post_screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostListingButton(
    onClick: () -> Unit,
    canPost: Boolean
) {

    Button(
        onClick = onClick,
        enabled = canPost,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text("Post Listing")
    }
}