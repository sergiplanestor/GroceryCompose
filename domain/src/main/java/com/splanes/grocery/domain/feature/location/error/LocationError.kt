package com.splanes.grocery.domain.feature.location.error

sealed class LocationError(override val message: String? = null) : Throwable(message) {

    data class Unknown(override val message: String?) : LocationError(message)
    object NotFound : LocationError()
    object InvalidRequest
    companion object
}

