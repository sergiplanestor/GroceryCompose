package com.splanes.grocery.domain.feature.user.error


sealed class UserError : Exception()
sealed class SignUpError : UserError() {
    object NotFound : SignUpError()
    object Unknown : SignUpError()
}

