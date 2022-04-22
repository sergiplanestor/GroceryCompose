package com.splanes.grocery.domain.feature.location.model

interface PlaceResult {
    val id: String
    val name: String
    val address: String
    val iconUrl: String
    val tags: List<Tag>
    val rating: Double?
    val priceLevel: Int?
    val coordinates: Coordinates

    enum class Tag {
        MealDelivery,
        MealTakeaway,
        Supermarket,
        LiquorStore,
        Other,;

        companion object
    }
}