package com.splanes.grocery.ui.feature.auth.contract

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.ui.component.form.model.Forms
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel

sealed class AuthUiModel : UiModel {
    data class SignIn(val user: User) : AuthUiModel()
    data class SignUp(val email: Forms.State<String>, val username: Forms.State<String>) : AuthUiModel()
}

sealed class AuthEvent : UiEvent {
    object OnBoardingAnimEnd : AuthEvent()
    data class SignIn(val user: User) : AuthEvent()
    data class SignUp(val email: String, val username: String) : AuthEvent()
}