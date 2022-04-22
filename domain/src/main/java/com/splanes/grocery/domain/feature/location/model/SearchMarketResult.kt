package com.splanes.grocery.domain.feature.location.model

import com.splanes.grocery.domain.feature.location.model.PlaceResult.Tag

data class SearchMarketResult(
    override val id: String,
    override val name: String,
    override val address: String,
    override val iconUrl: String,
    override val tags: List<Tag>,
    override val rating: Double?,
    override val priceLevel: Int?,
    override val coordinates: Coordinates,
): PlaceResult