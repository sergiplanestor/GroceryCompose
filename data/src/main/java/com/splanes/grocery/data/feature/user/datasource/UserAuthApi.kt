package com.splanes.grocery.data.feature.user.datasource

interface UserAuthApi {

    suspend fun isSignedIn(email: String): Boolean

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun signOut(): Boolean
}