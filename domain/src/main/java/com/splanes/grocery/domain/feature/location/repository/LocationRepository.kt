package com.splanes.grocery.domain.feature.location.repository

interface LocationRepository {
    suspend fun currentLocation()
    suspend fun searchPlaces()
}