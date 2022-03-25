package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<SignUpUseCase.Params, User> {

    override suspend fun execute(id: String, params: Params): User = TODO()
        //repository.signUp(params.email, params.username) ?: throw UserError.Unknown

    data class Params(val email: String, val username: String)

    companion object {
        const val ID = "UseCase.SignUp"
    }
}