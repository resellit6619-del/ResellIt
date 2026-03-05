package uk.ac.tees.mad.resellit.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.resellit.ui.theme.AppShapes

@Composable
fun EmailField(
    value : String ,
    onValueChange : (String) -> Unit ,
    modifier : Modifier = Modifier
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth() ,
        value = value ,
        onValueChange = onValueChange ,
        shape = AppShapes.InputFieldShape  ,
        singleLine = true ,
        maxLines = 1 ,
        placeholder = {
            if(value.isEmpty()){
                Text(
                    text = "abc@gmail.com"
                )
            }
        } ,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Email ,
                contentDescription = "email icon" ,
            )
        }
    )
}