package com.splanes.grocery.domain.utils.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timerange.TimeRange
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.Timestamp
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.now
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase

fun <Params, Data, UC : UseCase<Params, Data>> UC.request(
    params: Params,
    id: String = useCaseId(),
    timeout: TimeRange = useCaseTimeout()
): UseCase.Request<Params> =
    UseCase.Request(
        id,
        params,
        timeout = timeout
    )

fun <Data, UC : UseCase<Unit, Data>> UC.request(
    id: String = useCaseId(),
    timeout: TimeRange = useCaseTimeout()
): UseCase.Request<Unit> =
    request(
        params = Unit,
        id = id,
        timeout = timeout
    )

fun useCaseTimeout(): TimeRange = with(now()) {
    TimeRange(from = this, to = Timestamp(this.millis + 20000L))
}

fun <Params, Data, UC : UseCase<Params, Data>> UC.useCaseId(): String =
    "UseCase.${this::class.simpleName?.replace("UseCase", "") ?: "Unknown"}"

