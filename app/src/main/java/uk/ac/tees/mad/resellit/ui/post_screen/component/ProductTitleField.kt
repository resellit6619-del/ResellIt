package uk.ac.tees.mad.resellit.ui.post_screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.resellit.ui.theme.AppShapes

@Composable
fun ProductTitleField(
    value: String,
    onValueChange: (String) -> Unit
) {

    Column {
        Text(
            text = "Product Title",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = AppShapes.InputFieldShape,
            placeholder = {
                Text("What are you selling?")
            }
        )
    }
}