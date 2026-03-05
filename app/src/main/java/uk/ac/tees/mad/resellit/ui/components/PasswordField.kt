package uk.ac.tees.mad.resellit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import uk.ac.tees.mad.resellit.ui.theme.AppShapes

@Composable
fun PasswordField(
    title :String ,
    value : String ,
    onValueChange : (String) -> Unit ,
    isPasswordVisible : Boolean ,
    modifier : Modifier = Modifier ,
    onVisibilityChange : (Boolean) -> Unit
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
                    text = title
                )
            }
        } ,
        trailingIcon = {
            val icon = when{
                isPasswordVisible -> {
                    Icons.Default.Visibility
                }
                else -> {
                    Icons.Default.VisibilityOff
                }
            }

            Icon(
                imageVector = icon ,
                contentDescription = "visibility icon" ,
                modifier = Modifier.clickable{
                    onVisibilityChange(!isPasswordVisible)
                }
            )
        },
        visualTransformation = if(isPasswordVisible){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
    )
}