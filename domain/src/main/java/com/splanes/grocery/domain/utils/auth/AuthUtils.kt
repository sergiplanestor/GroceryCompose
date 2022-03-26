package com.splanes.grocery.domain.utils.auth

import android.util.Base64
import com.splanes.grocery.domain.feature.user.model.User
import java.security.MessageDigest

object AuthUtils {

    fun password(user: User): String =
        password(email = user.email, username = user.name)

    fun password(email: String, username: String): String {
        val pwd = email + username
        return MessageDigest
            .getInstance("SHA-256")
            .digest(pwd.toByteArray())
            .let { Base64.encodeToString(it, Base64.DEFAULT) }
    }
}