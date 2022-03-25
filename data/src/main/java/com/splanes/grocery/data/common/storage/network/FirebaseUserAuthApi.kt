package com.splanes.grocery.data.common.storage.network

import com.google.firebase.auth.FirebaseAuth
import com.splanes.grocery.data.feature.user.datasource.UserAuthApi
import com.splanes.grocery.data.utils.firebase.auth.OneShotStateListener
import com.splanes.grocery.data.utils.firebase.task.completeWithSuccess
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseUserAuthApi @Inject constructor(private val auth: FirebaseAuth) : UserAuthApi {

    override suspend fun isSignedIn(email: String): Boolean =
        auth.currentUser?.email == email

    override suspend fun signUp(email: String, password: String): Boolean =
        completeWithSuccess { auth.createUserWithEmailAndPassword(email, password) }

    override suspend fun signIn(email: String, password: String): Boolean =
        completeWithSuccess { auth.signInWithEmailAndPassword(email, password) }

    override suspend fun signOut(): Boolean = suspendCoroutine { continuation ->
        auth.addAuthStateListener(OneShotStateListener { continuation.resume(true) })
        auth.signOut()
    }
}