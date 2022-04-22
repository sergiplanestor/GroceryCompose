package com.splanes.grocery.domain.feature.location.model

data class Coordinates(
    val longitude: Double,
    val latitude: Double,
) {
    companion object {
        val Unknown: Coordinates get() =
            Coordinates(.0, .0)
    }
}
