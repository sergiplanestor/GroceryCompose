package com.splanes.grocery.data.feature.user.repositoryimpl

import com.splanes.grocery.data.common.repository.RepositoryImpl
import com.splanes.grocery.data.feature.user.datasource.UserLocalDataSource
import com.splanes.grocery.data.feature.user.datasource.UserNetworkDataSource
import com.splanes.grocery.data.feature.user.mapper.UserDtoMapper
import com.splanes.grocery.data.utils.execNestedBoolean
import com.splanes.grocery.data.utils.gson.toJson
import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.model.UserSearch
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkDataSource: UserNetworkDataSource,
    private val localDataSource: UserLocalDataSource,
    private val mapper: UserDtoMapper,
) : RepositoryImpl(), UserRepository {

    override suspend fun fetch(): User? = exec {
        localDataSource.fetch()?.run(mapper::map)
    }

    override suspend fun search(search: UserSearch): User? = exec {
        val user = localDataSource.search(search) ?: networkDataSource.search(search)
        user?.run(mapper::map)
    }

    override suspend fun insertOrUpdate(user: User): Boolean = exec {
        val dto = mapper.map(user)
        execNestedBoolean(
            { localDataSource.insertOrUpdate(dto) },
            { networkDataSource.insertOrUpdate(user.id, dto.toJson()) }
        )
    }
}