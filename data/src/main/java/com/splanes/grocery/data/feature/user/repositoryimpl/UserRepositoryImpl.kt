package com.splanes.grocery.data.feature.user.repositoryimpl

import com.splanes.grocery.data.common.repository.RepositoryImpl
import com.splanes.grocery.data.feature.user.datasource.UserAuthApi
import com.splanes.grocery.data.feature.user.datasource.UserLocalDataSource
import com.splanes.grocery.data.feature.user.datasource.UserNetworkDataSource
import com.splanes.grocery.data.feature.user.mapper.UserDtoMapper
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.model.UserSearch
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApi: UserAuthApi,
    private val networkDataSource: UserNetworkDataSource,
    private val localDataSource: UserLocalDataSource,
    private val mapper: UserDtoMapper,
) : RepositoryImpl(), UserRepository {

    override suspend fun fetchLocalUser(): User? = exec {
        localDataSource.fetchCurrent()?.run(mapper::map)
    }

    override suspend fun fetchUser(search: UserSearch): User? = exec {
        val user = localDataSource.search(search) ?: networkDataSource.search(search)
        user?.run(mapper::map)
    }

    override suspend fun signIn(email: String, password: String): Boolean =
        exec { authApi.signIn(email, password) }

    override suspend fun signUp(email: String, password: String): Boolean =
        exec { authApi.signUp(email, password) }
}