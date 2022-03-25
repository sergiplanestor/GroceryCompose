package com.splanes.grocery.domain.feature.user.error


sealed class UserError : Exception() {
    object NotRegistered : UserError()
    object Unknown : UserError()
}
