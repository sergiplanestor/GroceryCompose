package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.grocery.domain.utils.auth.AuthUtils
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<SignUpUseCase.Params, Boolean> {

    override suspend fun execute(id: String, params: Params): Boolean =
        repository.signUp(
            params.email,
            password = AuthUtils.password(email = params.email, username = params.username)
        )

    data class Params(val email: String, val username: String)
}