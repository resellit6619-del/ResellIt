package uk.ac.tees.mad.resellit.ui.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.setting.components.ClearDraftCard
import uk.ac.tees.mad.resellit.ui.setting.components.LogoutButton
import uk.ac.tees.mad.resellit.ui.setting.components.SettingTopBar
import uk.ac.tees.mad.resellit.ui.setting.components.UserProfileCard
import uk.ac.tees.mad.resellit.ui.theme.Dimens


@Composable
fun SettingScreen(
    onNavToLogin: () -> Unit,
    viewModel: SettingViewModel = viewModel()
) {
    val uiState by viewModel.settingUiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToLogin) {
        if (uiState.navigateToLogin) {
            onNavToLogin()
        }
    }

    SettingContent(
        onLogoutClick = viewModel::onLogoutClick,
        onToggle = viewModel::onDialogToggle,
        isLoading = uiState.isLoading ,
        profile = uiState.profile ,
        isDialogOpen = uiState.isDialogOpen ,
        onConfirm = {
            viewModel.onClearDraft()
            viewModel.onDialogToggle()
        } ,
        isRefreshing = uiState.isRefreshing
    )
}


@Composable
fun SettingContent(
    onLogoutClick: () -> Unit,
    onToggle: () -> Unit,
    isLoading: Boolean,
    isDialogOpen: Boolean,
    onConfirm: () -> Unit,
    profile: String ,
    isRefreshing : Boolean
) {

    Box(modifier = Modifier.fillMaxSize()) {

        if(isDialogOpen){
            DeleteConfirmationDialog(
                onConfirm = onConfirm,
                onDismiss = onToggle
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            SettingTopBar()
            Spacer(modifier = Modifier.height(Dimens.Small))
            UserProfileCard(profile = profile)
            ClearDraftCard(onClick = onToggle ,
                isRefreshing = isRefreshing)
            Spacer(modifier = Modifier.weight(1f))
            LogoutButton(onLogoutClick = onLogoutClick)
            Spacer(modifier = Modifier.height(Dimens.Small))
        }

        if (isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Delete Draft")
        },
        text = {
            Text("Are you sure you want to delete this draft?")
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("No")
            }
        }
    )
}



@Composable
@Preview(showBackground = true)
fun SettingPreview() {
    SettingContent(
        onLogoutClick = {},
        onToggle = {},
        isLoading = false,
        isDialogOpen = false,
        onConfirm = {},
        profile = "james.iredell@examplepetstore.com" ,
        isRefreshing = false
    )
}
