package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.user.error.UserError
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<FetchUserUseCase.Params, User> {

    override suspend fun execute(id: String, params: Params): User =
        repository.fetchLocalUser() ?: throw UserError.NotRegistered

    object Params

    companion object {
        const val ID = "UseCase.FetchUser"
    }
}