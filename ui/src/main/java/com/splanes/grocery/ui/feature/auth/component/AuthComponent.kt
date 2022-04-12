package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.splanes.grocery.ui.feature.auth.component.subcomponents.AuthAutoSignInComponent
import com.splanes.grocery.ui.feature.auth.component.subcomponents.AuthHeaderComponent
import com.splanes.grocery.ui.feature.auth.contract.AuthEvent
import com.splanes.grocery.ui.feature.auth.contract.AuthRedirections
import com.splanes.grocery.ui.feature.auth.contract.AuthUiModel
import com.splanes.grocery.ui.feature.auth.viewmodel.AuthViewModel
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.ui.components.feature.statusbar.utils.statusBarColors
import timber.log.Timber

@Composable
fun GroceryAuthComponent(onNavToDashboard: () -> Unit) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val uiEvent: (AuthEvent) -> Unit = viewModel::onUiEvent

    with (viewModel.uiSideEffect) {
        LaunchedEffect(this) {
            collect { effect ->
                when (effect) {
                    AuthRedirections.Dashboard -> onNavToDashboard()
                }
            }
        }
    }

    AuthStatusBar()

    AuthContainerComponent(
        header = { AuthHeaderComponent(onAnimFinish = { viewModel.checkUserAccountStatus() }) },
        content = { scrollState ->
            val state = viewModel.uiState.value
            Crossfade(targetState = state, animationSpec = tween(duration = 750)) { uiState ->
                when (uiState) {
                    is UiState.Uninitialized -> {
                        /* Nothing to do here */
                    }
                    is UiState.Ready -> AuthActivityContent(
                        model = uiState.data,
                        scrollState = scrollState,
                        uiEvent = uiEvent
                    )
                    is UiState.Loading -> AuthLoaderComponent(message = string { Strings.loading })
                    is UiState.Error -> {
                        Timber.e(uiState.cause)
                    }
                }
            }
        }
    )
}

@Composable
fun AuthStatusBar() {
    val uiController = rememberSystemUiController()
    uiController.statusBarColors(
        color = color { primary },
        transformation = null
    )
}

    @Composable
fun AuthActivityContent(
    model: AuthUiModel,
    scrollState: ScrollState,
    uiEvent: (AuthEvent) -> Unit
) {
    when (model) {
        AuthUiModel.AutoSignIn -> AuthAutoSignInComponent(onSignIn = { uiEvent(AuthEvent.SignIn) })
        is AuthUiModel.SignUp -> AuthSignUpComponent(
            username = model.username,
            email = model.email,
            onSignUp = { username, email ->
                uiEvent(AuthEvent.SignUp(username = username, email = email))
            },
            scrollState = scrollState
        )
    }
}