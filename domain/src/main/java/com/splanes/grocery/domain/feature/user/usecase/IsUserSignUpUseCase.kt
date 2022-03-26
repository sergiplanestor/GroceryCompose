package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.error.UserError
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class IsUserSignUpUseCase @Inject constructor(
    private val fetchUserUseCase: FetchUserUseCase
) : UseCase<Unit, Boolean> {

    override suspend fun execute(id: String, params: Unit): Boolean =
        runCatching { fetchUserUseCase.execute(id, Unit) }.run {
            exceptionOrNull()?.takeIf { it !is UserError.NotSignUp }?.let { throw it }
            isSuccess
        }
}