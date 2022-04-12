package com.splanes.grocery.domain.feature.product.model

import com.splanes.grocery.domain.feature.market.model.Market

data class ProductPrice(
    val estimated: Double,
    val marketPriceMap: Map<Market, Double> = emptyMap(),
) {
    fun marketPriceAverage(): Double = marketPriceMap.values.average()

    fun hasMarket(market: Market): Boolean = marketPriceMap.getOrDefault(market, NO_PRICE).isValid()

    fun addOrUpdateMarket(market: Market, price: Double): ProductPrice = copy(
        marketPriceMap = marketPriceMap.toMutableMap().apply { put(market, price) }
    )

    fun removeMarket(market: Market): ProductPrice = copy(
        marketPriceMap = marketPriceMap.toMutableMap().apply { remove(market) }
    )

    fun Double?.isValid(): Boolean =
        (this ?: NO_PRICE).run { isFinite() && this > 0.0 }

    companion object {
        const val NO_PRICE: Double = Double.NaN
        val Undefined by lazy { ProductPrice(estimated = NO_PRICE) }
    }
}
