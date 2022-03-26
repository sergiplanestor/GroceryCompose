package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.grocery.domain.utils.auth.AuthUtils
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<SignInUseCase.Params, Boolean> {

    override suspend fun execute(id: String, params: Params): Boolean = with(params.user) {
        repository.signIn(email = email, password = AuthUtils.password(this))
    }

    data class Params(val user: User)
}