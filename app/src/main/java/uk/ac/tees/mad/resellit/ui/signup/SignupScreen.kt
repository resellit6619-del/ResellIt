package uk.ac.tees.mad.resellit.ui.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.components.EmailField
import uk.ac.tees.mad.resellit.ui.components.PasswordField
import uk.ac.tees.mad.resellit.ui.signup.component.SignupHeader
import uk.ac.tees.mad.resellit.ui.theme.AppShapes
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = viewModel() ,
    onNavigateToLogin : () -> Unit ,
    onNavigateToHome : () -> Unit
){
    val uiState by viewModel.signupUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.navigateToHome) {
        if (uiState.navigateToHome){
            onNavigateToHome()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Toast.makeText(
                context ,
                uiState.error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    SignupScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel ::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
        onConfirmPasswordVisibilityChange = viewModel::onConfirmPasswordVisibilityChange,
        onRegisterClick = viewModel::register,
        onNavigateToLoginClick = onNavigateToLogin
    )

}


@Composable
fun SignupScreenContent(uiState : SignupUiState ,
                        onEmailChange:(String) -> Unit ,
                        onPasswordChange:(String) -> Unit ,
                        onConfirmPasswordChange:(String) -> Unit,
                        onPasswordVisibilityChange:(Boolean) -> Unit,
                        onConfirmPasswordVisibilityChange:(Boolean) -> Unit,
                        onRegisterClick: () -> Unit ,
                        onNavigateToLoginClick : () -> Unit){
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenHorizontalPadding) ,
        contentAlignment = Alignment.Center
    ){
        Card(
           modifier = Modifier
               .fillMaxWidth()
                .imePadding(),
            shape = AppShapes.CardShape,
            colors = CardDefaults
                .cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
            elevation = CardDefaults
                .cardElevation(
                    defaultElevation = Dimens.CardElevation
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenHorizontalPadding) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(Modifier.height(Dimens.Small))
                SignupHeader()
                Spacer(modifier = Modifier.height(Dimens.Small))
                EmailField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                )
                Spacer(modifier = Modifier.height(Dimens.Small))
                PasswordField(
                    title = "password",
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    isPasswordVisible = uiState.isPasswordVisible,
                    onVisibilityChange = onPasswordVisibilityChange
                )
                Spacer(modifier = Modifier.height(Dimens.Small))
                PasswordField(
                    title = "confirm password",
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    isPasswordVisible = uiState.isConfirmPasswordVisible,
                    onVisibilityChange = onConfirmPasswordVisibilityChange
                )
                Spacer(modifier = Modifier.height(Dimens.Medium))
                Button(
                    onClick = onRegisterClick ,
                    enabled = !uiState.isLoading && uiState.canSubmit,
                    modifier = Modifier.fillMaxWidth()
                ){
                    when{
                        uiState.isLoading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary ,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        else -> {
                            Text(
                                text = "Register" ,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.Small))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = Dimens.ExtraSmall),
                    horizontalArrangement = Arrangement.Center ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        fontSize = 14.sp ,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = onNavigateToLoginClick) {
                        Text(
                            text = "Login",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}



@Composable
@Preview(showBackground = true)
fun SignupScreenPreview(){
    SignupScreenContent(
        uiState = SignupUiState(),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onPasswordVisibilityChange = {},
        onConfirmPasswordVisibilityChange = {},
        onRegisterClick = {},
        onNavigateToLoginClick = {}
    )
}

/**
 * this is register screen having parent layout box fill to max
 */