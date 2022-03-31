package com.splanes.grocery.data.feature.user.datasource

import com.splanes.grocery.data.feature.user.entity.UserDto
import com.splanes.grocery.domain.feature.user.model.UserSearch

interface UserLocalDataSource {

    suspend fun fetch(): UserDto?

    suspend fun search(search: UserSearch): UserDto?

    suspend fun insertOrUpdate(data: UserDto): Boolean

}