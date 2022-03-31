package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.auth.AuthServiceAgent
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authAgent: AuthServiceAgent) : UseCase<SignInUseCase.Params, Boolean> {

    override suspend fun execute(id: String, params: Params): Boolean =
        authAgent.signIn(email = params.user.email)

    data class Params(val user: User)
}