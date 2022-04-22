package com.splanes.grocery.domain.feature.market.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class SearchNearMarketsUseCase @Inject constructor() : UseCase<SearchNearMarketsUseCase.Params, Any> {

    override suspend fun execute(id: String, params: Params): Any {
        TODO("Not yet implemented")
    }

    data class Params(val id: String)
}