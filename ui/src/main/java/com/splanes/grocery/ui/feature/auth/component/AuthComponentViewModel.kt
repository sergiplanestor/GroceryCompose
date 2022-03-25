package com.splanes.grocery.ui.feature.auth.component

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.usecase.FetchUserUseCase
import com.splanes.grocery.domain.feature.user.usecase.SignInUseCase
import com.splanes.grocery.domain.feature.user.usecase.SignUpUseCase
import com.splanes.grocery.ui.feature.auth.contract.AuthEvent
import com.splanes.grocery.ui.feature.auth.contract.AuthUiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.utils.EmptyUiSideEffect
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthComponentViewModel @Inject constructor(
    private val fetchUser: FetchUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : ComponentViewModel<AuthUiModel, AuthEvent, EmptyUiSideEffect>() {

    override fun onUiEventHandled(uiEvent: AuthEvent) {
        when (uiEvent) {
            is AuthEvent.SignIn -> doSignIn(uiEvent.user)
            is AuthEvent.SignUp -> doSignUp(uiEvent.email, uiEvent.username)
        }
    }

    private fun doSignIn(user: User) {

    }

    private fun doSignUp(email: String, username: String) {

    }
}