package com.splanes.grocery.domain.feature.user.error


sealed class UserError : Exception() {
    object NotSignUp : UserError()
    object Unknown : UserError()
}
