package com.splanes.grocery.ui.feature.dashboard.contract

import com.splanes.grocery.ui.component.form.model.Forms
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect

sealed class AuthUiModel : UiModel {
    object AutoSignIn: AuthUiModel()
    data class SignUp(
        val email: Forms.State<String> = Forms.Idle(value = ""),
        val username: Forms.State<String> = Forms.Idle(value = "")
    ) : AuthUiModel()
}

sealed class AuthEvent : UiEvent {
    object SignIn : AuthEvent()
    data class SignUp(val email: String, val username: String) : AuthEvent()
}

sealed class AuthRedirections : UiSideEffect {
    object Dashboard : AuthRedirections()
}