package com.splanes.grocery.domain.feature.location.usecase

import com.splanes.grocery.domain.feature.location.repository.LocationRepository
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) : UseCase<GetCurrentLocationUseCase.Params, Any> {

    override suspend fun execute(id: String, params: Params): Any {

        return false
    }

    object Params
}