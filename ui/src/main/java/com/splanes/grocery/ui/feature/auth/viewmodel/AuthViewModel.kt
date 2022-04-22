package com.splanes.grocery.ui.feature.auth.viewmodel

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.usecase.FetchUserUseCase
import com.splanes.grocery.domain.feature.user.usecase.IsUserSignUpUseCase
import com.splanes.grocery.domain.feature.user.usecase.SignInUseCase
import com.splanes.grocery.domain.feature.user.usecase.SignUpUseCase
import com.splanes.grocery.domain.utils.usecase.async
import com.splanes.grocery.domain.utils.usecase.handle
import com.splanes.grocery.domain.utils.usecase.request
import com.splanes.grocery.ui.feature.auth.contract.AuthEvent
import com.splanes.grocery.ui.feature.auth.contract.AuthRedirections
import com.splanes.grocery.ui.feature.auth.contract.AuthUiModel
import com.splanes.grocery.utils.scope.withResult
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val isUserSignUpUseCase: IsUserSignUpUseCase,
    private val fetchUser: FetchUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : ComponentViewModel<AuthUiModel, AuthEvent, AuthRedirections>() {

    override fun onUiEventHandled(uiEvent: AuthEvent) {
        when (uiEvent) {
            is AuthEvent.SignUp -> doSignUp(uiEvent.email, uiEvent.username)
            AuthEvent.SignIn -> fetchUserAndSignIn()
        }
    }

    fun checkUserAccountStatus() {
        launch {
            val isSignedUp = isUserSignedUpAsync()
            val uiModel = if (isSignedUp) {
                AuthUiModel.AutoSignIn
            } else {
                AuthUiModel.SignUp()
            }
            updateUiState { UiState.Ready(uiModel) }
        }
    }

    private suspend fun isUserSignedUpAsync(): Boolean =
        isUserSignUpUseCase.async(
            req = isUserSignUpUseCase.request(),
            onError = { err -> withResult(result = null) { errorUiState(err.cause) } }
        ) ?: false


    private fun fetchUserAndSignIn() {
        fetchUser.launch(
            req = fetchUser.request(),
            mapper = { handle(onError = { e -> errorUiState(e.cause) }) { user -> doSignIn(user) } }
        )
    }

    private fun doSignIn(user: User) = signInUseCase.launch(
        req = signInUseCase.request(SignInUseCase.Params(user)),
        mapper = { handle(onError = { e -> errorUiState(e.cause) }) { onUiSideEffect { AuthRedirections.Dashboard } } }
    )

    private fun doSignUp(email: String, username: String) = signUpUseCase.launch(
        req = signUpUseCase.request(SignUpUseCase.Params(email, username)),
        mapper = { handle(onError = { e -> errorUiState(e.cause) }) { onUiSideEffect { AuthRedirections.Dashboard } } }
    )

    private fun errorUiState(cause: Throwable?) =
        updateUiState { UiState.Error(cause) }
}