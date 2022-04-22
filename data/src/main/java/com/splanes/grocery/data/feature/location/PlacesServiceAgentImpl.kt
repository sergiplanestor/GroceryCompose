package com.splanes.grocery.data.feature.location

import android.annotation.SuppressLint
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.splanes.grocery.data.utils.firebase.task.completable
import com.splanes.grocery.domain.feature.location.agent.PlacesServiceAgent
import com.splanes.grocery.domain.feature.location.model.SearchMarketResult
import javax.inject.Inject

@SuppressLint("MissingPermission")
class PlacesServiceAgentImpl @Inject constructor(
    private val client: PlacesClient,
    private val mapper: PlacesMapper
) : PlacesServiceAgent {

    override suspend fun searchCurrentMarkets(): List<SearchMarketResult> =
        client.findCurrentPlaces().map(mapper::map)

    private suspend fun PlacesClient.findCurrentPlaces(
        fields: List<Place.Field> = PlaceFieldsRequest,
        satisfies: PlaceLikelihood.() -> Boolean = { hasMinLikelihood() && hasAcceptedType() }
    ): List<Place> {
        val response = completable { findCurrentPlace(FindCurrentPlaceRequest.newInstance(fields)) }.placeLikelihoods
        return response.filter { result -> result.satisfies() }.map { it.place }
    }

    private fun PlaceLikelihood.hasMinLikelihood(): Boolean =
        likelihood >= PlaceMinLikelihood

    private fun PlaceLikelihood.hasAcceptedType(): Boolean =
        place.types?.any { type -> PlaceAcceptedTypes.contains(type) } ?: false

    companion object {
        private const val PlaceMinLikelihood: Double = 0.35
        private val PlaceAcceptedTypes: List<Place.Type> by lazy {
            listOf(
                Place.Type.MEAL_TAKEAWAY,
                Place.Type.MEAL_DELIVERY,
                Place.Type.SUPERMARKET,
                Place.Type.LIQUOR_STORE,
                Place.Type.OTHER,
            )
        }
        private val PlaceFieldsRequest: List<Place.Field> by lazy {
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
                Place.Field.ICON_URL,
                Place.Field.TYPES,
                Place.Field.RATING,
                Place.Field.PRICE_LEVEL,
            )
        }
    }
}