package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.error.SignUpError
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.grocery.utils.logger.utils.orError
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<Unit, User> {

    override suspend fun execute(id: String, params: Unit): User =
        repository.fetch().orError { SignUpError.NotFound }
}