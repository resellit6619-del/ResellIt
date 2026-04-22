package uk.ac.tees.mad.resellit.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AddListingFab(
    onClick: () -> Unit
) {
    Button(onClick = onClick ,
        shape = RoundedCornerShape(8.dp) ,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary ,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )) {
        Icon(imageVector = Icons.Default.Edit ,
            contentDescription = "edit/add")
        Text(text = "Add")
    }
}