package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<SignInUseCase.Params, User> {

    override suspend fun execute(id: String, params: Params): User = TODO()
        //repository.signIn(params.user) ?: throw UserError.Unknown

    data class Params(val user: User)

    companion object {
        const val ID = "UseCase.SignIn"
    }
}