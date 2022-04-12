package com.splanes.grocery.domain.feature.product.model

import com.splanes.grocery.domain.feature.market.model.Market

data class Product(
    val id: String,
    val name: String,
    val thumbnail: String = "",
    val category: ProductCategory = ProductCategory.Undefined,
    val description: String = "",
    val price: ProductPrice = ProductPrice.Undefined,
    val markets: List<Market> = emptyList(),
    val tags: List<ProductTag> = emptyList(),
    val isHighlight: Boolean = false,
    //val createdBy: UserBasicInfo,
    val createdOn: Long,
) {

    fun hasMarket(market: Market): Boolean =
        markets.contains(market)

    fun hasTag(tag: ProductTag): Boolean =
        tags.contains(tag)

    fun addMarket(market: Market): Product =
        copy(markets = markets.toMutableList().apply { add(market) })

    fun addTag(tag: ProductTag): Product =
        copy(tags = tags.toMutableList().apply { add(tag) })

}
