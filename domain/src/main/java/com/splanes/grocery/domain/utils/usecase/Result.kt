package com.splanes.grocery.domain.utils.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase

fun <Data> UseCase.Result<Data>.handle(
    onError: (UseCase.Error<Data>) -> Unit = { },
    onSuccess: (Data) -> Unit,
) {
    when (this) {
        is UseCase.Success -> onSuccess(data)
        is UseCase.Error -> onError(this)
    }
}