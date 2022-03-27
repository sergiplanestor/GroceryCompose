package com.splanes.grocery.ui.feature.auth.component

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.usecase.FetchUserUseCase
import com.splanes.grocery.domain.feature.user.usecase.IsUserSignUpUseCase
import com.splanes.grocery.domain.feature.user.usecase.SignInUseCase
import com.splanes.grocery.domain.feature.user.usecase.SignUpUseCase
import com.splanes.grocery.domain.utils.usecase.request
import com.splanes.grocery.ui.feature.auth.contract.AuthEvent
import com.splanes.grocery.ui.feature.auth.contract.AuthRedirections
import com.splanes.grocery.ui.feature.auth.contract.AuthUiModel
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthComponentViewModel @Inject constructor(
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

    fun findUser() {
        isUserSignUpUseCase.launch(
            req = isUserSignUpUseCase.request(),
            mapper = {
                handle { isSignedUp ->
                    if (!isSignedUp) updateUiState {
                        UiState.Ready(
                            AuthUiModel.SignUp()
                        )
                    }
                }
            }
        )
    }

    private fun fetchUserAndSignIn() {
        fetchUser.launch(
            req = fetchUser.request(),
            mapper = { handle { user -> doSignIn(user) } }
        )
    }

    private fun doSignIn(user: User) = signInUseCase.launch(
        req = signInUseCase.request(SignInUseCase.Params(user)),
        mapper = { handle { onUiSideEffect { AuthRedirections.Dashboard } } }
    )

    private fun doSignUp(email: String, username: String) {

    }

    private fun <Data> UseCase.Result<Data>.handle(
        onError: (UseCase.Error<Data>) -> Unit = { err -> updateUiState { UiState.Error(err.cause) } },
        onSuccess: (Data) -> Unit,
    ) {
        when (this) {
            is UseCase.Success -> onSuccess(data)
            is UseCase.Error -> onError(this)
        }
    }
}