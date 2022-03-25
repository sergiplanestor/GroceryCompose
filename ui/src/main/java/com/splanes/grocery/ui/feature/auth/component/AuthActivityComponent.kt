package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.splanes.grocery.ui.feature.auth.contract.AuthEvent
import com.splanes.grocery.ui.feature.auth.contract.AuthUiModel
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState

@Composable
fun AuthActivityComponent() {

    val viewModel = hiltViewModel<AuthComponentViewModel>()
    val uiState by viewModel.uiState
    val uiEvent: (AuthEvent) -> Unit = viewModel::onUiEvent

    AuthContainerComponent(
        header = {
            AuthHeaderComponent(onAnimEnd = { viewModel.onUiEvent(AuthEvent.OnBoardingAnimEnd) })
        },
        content = {
            Crossfade(
                modifier = Modifier.fillMaxWidth(),
                targetState = uiState
            ) { state ->
                when (state) {
                    is UiState.Uninitialized -> AuthLoaderComponent(message = string { Strings.searching_user })
                    is UiState.Ready -> when (val model = state.data) {
                        is AuthUiModel.SignIn -> AuthSignInComponent()
                        is AuthUiModel.SignUp -> AuthSignUpComponent(
                            username = model.username,
                            email = model.email,
                            onSignUp = { username, email ->
                                uiEvent(AuthEvent.SignUp(username = username, email = email))
                            }
                        )
                    }
                    is UiState.Loading -> AuthLoaderComponent(message = string { Strings.loading })
                    is UiState.Error -> TODO()
                }
            }
        }
    )
}