package uk.ac.tees.mad.resellit.ui.post_screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.resellit.ui.theme.AppShapes

@Composable
fun PriceField(
    value: String,
    onValueChange: (String) -> Unit ,
    modifier: Modifier
) {

    Column (modifier){

        Text("Price")

        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = AppShapes.InputFieldShape,
            maxLines = 1,
            singleLine = true,
            placeholder = {
                Text("$ 0.00")
            }
        )
    }
}