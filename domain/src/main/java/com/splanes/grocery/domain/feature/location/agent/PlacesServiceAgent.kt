package com.splanes.grocery.domain.feature.location.agent

import com.splanes.grocery.domain.feature.location.model.SearchMarketResult

interface PlacesServiceAgent {
    suspend fun searchCurrentMarkets(): List<SearchMarketResult>
}