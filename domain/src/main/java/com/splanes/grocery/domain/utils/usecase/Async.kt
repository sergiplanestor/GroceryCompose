package com.splanes.grocery.domain.utils.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase

suspend fun <Params, Data, UC : UseCase<Params, Data>> UC.async(
    onError: (UseCase.Error<Data>) -> Data? = { null },
    req: UseCase.Request<Params>,
): Data? =
    when(val result = invoke(req)) {
        is UseCase.Success -> result.data
        is UseCase.Error -> onError(result)
    }