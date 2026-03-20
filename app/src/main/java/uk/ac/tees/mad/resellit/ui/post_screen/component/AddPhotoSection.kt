package uk.ac.tees.mad.resellit.ui.post_screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun AddPhotoSection(
    onSelectImages: () -> Unit,
    isImagePicked: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Color.LightGray,
                RoundedCornerShape(Dimens.Medium)
            )
            .padding(Dimens.ScreenHorizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Add Product Photos",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Upload up to 5 images of your item",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onSelectImages
        ) {
            Text(if (isImagePicked) "Picked Images" else "Pick Images")
        }
    }
}