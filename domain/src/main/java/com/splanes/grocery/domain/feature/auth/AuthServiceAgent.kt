package com.splanes.grocery.domain.feature.auth

interface AuthServiceAgent {
    suspend fun isSignedIn(email: String): Boolean
    suspend fun signIn(email: String): Boolean
    suspend fun signUp(email: String): String
    suspend fun signOut(): Boolean
}