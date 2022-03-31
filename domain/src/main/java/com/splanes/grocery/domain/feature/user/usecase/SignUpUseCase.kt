package com.splanes.grocery.domain.feature.user.usecase

import com.splanes.grocery.domain.feature.auth.AuthServiceAgent
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.now
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authAgent: AuthServiceAgent,
    private val repository: UserRepository
) : UseCase<SignUpUseCase.Params, Boolean> {

    override suspend fun execute(id: String, params: Params): Boolean = with(params) {
        val uid = authAgent.signUp(email = email)
        val user = User(
            id = uid,
            name = username,
            email = email,
            createdOn = now().millis
        )
        repository.insertOrUpdate(user)
    }

    data class Params(val email: String, val username: String)
}