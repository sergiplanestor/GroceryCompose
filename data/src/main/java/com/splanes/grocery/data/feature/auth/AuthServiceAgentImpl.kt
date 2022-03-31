package com.splanes.grocery.data.feature.auth

import android.util.Base64
import com.google.firebase.auth.FirebaseAuth
import com.splanes.grocery.data.utils.firebase.auth.OneShotStateListener
import com.splanes.grocery.data.utils.firebase.task.completable
import com.splanes.grocery.data.utils.firebase.task.completeWithSuccess
import com.splanes.grocery.domain.feature.auth.AuthServiceAgent
import com.splanes.grocery.domain.feature.user.error.SignUpError
import com.splanes.grocery.utils.logger.utils.orError
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthServiceAgentImpl @Inject constructor(private val auth: FirebaseAuth) : AuthServiceAgent {

    override suspend fun isSignedIn(email: String): Boolean =
        auth.currentUser?.email == email

    override suspend fun signUp(email: String): String =
        completable { auth.createUserWithEmailAndPassword(email, password(email)) }.run {
            user?.uid.orError { SignUpError.Unknown }
        }

    override suspend fun signIn(email: String): Boolean =
        completeWithSuccess { auth.signInWithEmailAndPassword(email, password(email)) }

    override suspend fun signOut(): Boolean = suspendCoroutine { continuation ->
        auth.addAuthStateListener(OneShotStateListener { continuation.resume(true) })
        auth.signOut()
    }

    private fun password(email: String): String {
        val pwd = PWD_PREFIX + email +  PWD_MIDDLE + email.reversed() + PWD_SUFFIX
        return MessageDigest
            .getInstance("SHA-256")
            .digest(pwd.toByteArray())
            .let { Base64.encodeToString(it, Base64.DEFAULT) }
    }

    companion object {
        private const val PWD_PREFIX = "bLTOZKKX"
        private const val PWD_MIDDLE = "eltGboYk"
        private const val PWD_SUFFIX = "rnSRFjTL"
    }
}