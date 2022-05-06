package com.splanes.grocery.domain.feature.market.model.util

import com.splanes.grocery.domain.feature.market.model.Market

fun Market.Brand.Companion.all(): List<Market.Brand> = listOf(
    Market.Condis,
    Market.Carrefour,
    Market.OrigenAmetller,
    Market.GranjaArmengol,
    Market.Dia,
    Market.Bonpreu,
    Market.Esclat,
    Market.Lidl,
    Market.Mercadona,
    Market.Consum,
    Market.Bosch,
    Market.Xarcuteca,
    Market.Taranna,
    Market.CorteIngles,
)

fun Market.Brand.Companion.find(
    fallback: Market.Brand = Market.Brand.Undefined,
    predicate: (Market.Brand) -> Boolean
): Market.Brand =
    all().find(predicate) ?: fallback