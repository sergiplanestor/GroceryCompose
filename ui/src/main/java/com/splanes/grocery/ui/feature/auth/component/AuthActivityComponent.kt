package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.splanes.grocery.ui.feature.auth.contract.AuthEvent
import com.splanes.grocery.ui.feature.auth.contract.AuthUiModel
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.ui.components.feature.statusbar.utils.statusBarColors
import timber.log.Timber

@Composable
fun AuthActivityComponent() {

    val viewModel = hiltViewModel<AuthComponentViewModel>()
    val uiEvent: (AuthEvent) -> Unit = viewModel::onUiEvent
    val uiController = rememberSystemUiController()

    uiController.statusBarColors(
        color = color { primary },
        transformation = null
    )

    AuthContainerComponent(
        header = { AuthHeaderComponent() },
        content = {
            when (val uiState = viewModel.uiState.value) {
                is UiState.Uninitialized -> { /* Nothing to do here */ }
                is UiState.Ready -> AuthActivityContent(model = uiState.data, uiEvent = uiEvent)
                is UiState.Loading -> AuthLoaderComponent(message = string { Strings.loading })
                is UiState.Error -> { Timber.e(uiState.cause) }
            }
        }
    )
}

@Composable
fun AuthActivityContent(model: AuthUiModel, uiEvent: (AuthEvent) -> Unit) {
    when (model) {
        is AuthUiModel.SignIn -> AuthSignInComponent()
        is AuthUiModel.SignUp -> AuthSignUpComponent(
            username = model.username,
            email = model.email,
            onSignUp = { username, email ->
                uiEvent(AuthEvent.SignUp(username = username, email = email))
            }
        )
    }
}