package uk.ac.tees.mad.resellit.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.components.EmailField
import uk.ac.tees.mad.resellit.ui.login.component.LoginHeader
import uk.ac.tees.mad.resellit.ui.components.PasswordField
import uk.ac.tees.mad.resellit.ui.theme.AppShapes
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel() ,
    onNavigateToHome : () -> Unit ,
    onNavigateToRegister : () -> Unit
){
    val uiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToHome) {
        if(uiState.navigateToHome){
            onNavigateToHome()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onPasswordChange = viewModel::onPasswordChange,
        onEmailChange = viewModel::onEmailChange,
        onVisibilityChange = viewModel::onVisibilityChange,
        onNavigateToRegister = onNavigateToRegister,
        onLoginClick = viewModel::onLoginClick
    )

}


@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onLoginClick: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    onNavigateToRegister: () -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenHorizontalPadding),
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
            ) {
                Spacer(Modifier.height(Dimens.Small))
                LoginHeader()
                Spacer(modifier = Modifier.height(Dimens.Small))
                EmailField(value = uiState.email , onValueChange = onEmailChange)
                Spacer(modifier = Modifier.height(Dimens.Small))
                PasswordField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    isPasswordVisible = uiState.isPasswordVisible,
                    onVisibilityChange = onVisibilityChange,
                    title = "password",
                )
                Spacer(modifier = Modifier.height(Dimens.Medium))
                Button(
                    onClick = onLoginClick,
                    enabled = uiState.canSubmit && !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    when{
                        uiState.isLoading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary ,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        else ->{
                            Text(
                                text = "Login",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.Small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 14.sp ,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = onNavigateToRegister) {
                        Text(
                            text = "Create Account",
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
fun LoginScreenPreview(){
    LoginScreenContent(
        uiState = LoginUiState(),
        onLoginClick = {},
        onPasswordChange = {},
        onEmailChange = {},
        onVisibilityChange = {},
        onNavigateToRegister = {}
    )
}


/**
 * this is a login screen having parent layout box fill to max and given nav bar padding
 * and status bar padding and after that a card layout is there inside the card
 * column layout  is implemented to keep element on after another in a column
 **/