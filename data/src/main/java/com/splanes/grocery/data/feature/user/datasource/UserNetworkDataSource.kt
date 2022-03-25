package com.splanes.grocery.data.feature.user.datasource

import com.splanes.grocery.data.feature.user.entity.UserDto
import com.splanes.grocery.domain.feature.user.model.UserSearch

interface UserNetworkDataSource {

    suspend fun insertOrUpdate(id: String, data: String): Boolean

    suspend fun search(search: UserSearch): UserDto?

}