package com.splanes.grocery.domain.feature.user.repository

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.model.UserSearch

interface UserRepository {
    suspend fun fetch(): User?
    suspend fun search(search: UserSearch): User?
    suspend fun insertOrUpdate(user: User): Boolean
}