package com.splanes.grocery.data.feature.location

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.Place.Type
import com.splanes.grocery.domain.feature.location.error.LocationError
import com.splanes.grocery.domain.feature.location.model.Coordinates
import com.splanes.grocery.domain.feature.location.model.PlaceResult
import com.splanes.grocery.domain.feature.location.model.SearchMarketResult
import javax.inject.Inject

class PlacesMapper @Inject constructor() {

    fun map(apiPlace: Place): SearchMarketResult = with(apiPlace) {
        SearchMarketResult(
            id = id.orEmpty(),
            name = name.orEmpty(),
            address = address.orEmpty(),
            iconUrl = iconUrl.orEmpty(),
            tags = types.orEmpty().map { PlaceResult.Tag.map(it) },
            rating = rating,
            priceLevel = priceLevel,
            coordinates = Coordinates.map(latLng),
        )
    }

    fun Coordinates.Companion.map(latLng: LatLng?): Coordinates =
        latLng?.run { Coordinates(longitude = longitude, latitude = latitude) } ?: Coordinates.Unknown

    fun PlaceResult.Tag.Companion.map(type: Type): PlaceResult.Tag =
        when(type) {
            Place.Type.MEAL_TAKEAWAY -> PlaceResult.Tag.MealTakeaway
            Place.Type.MEAL_DELIVERY -> PlaceResult.Tag.MealDelivery
            Place.Type.SUPERMARKET -> PlaceResult.Tag.Supermarket
            Place.Type.LIQUOR_STORE -> PlaceResult.Tag.LiquorStore
            Place.Type.OTHER -> PlaceResult.Tag.Other
            else -> throw LocationError.Unknown(null)
        }
}