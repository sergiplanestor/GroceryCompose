package com.splanes.grocery.domain.feature.user.repository

import com.splanes.grocery.domain.feature.user.model.User
import com.splanes.grocery.domain.feature.user.model.UserSearch

interface UserRepository {
    suspend fun fetchLocalUser(): User?
    suspend fun fetchUser(search: UserSearch): User?
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
}