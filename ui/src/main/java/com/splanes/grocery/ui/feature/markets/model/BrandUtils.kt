package com.splanes.grocery.ui.feature.markets.model

import com.splanes.grocery.domain.feature.market.model.Market
import com.splanes.grocery.domain.feature.market.model.Market.Bonpreu
import com.splanes.grocery.domain.feature.market.model.Market.Bosch
import com.splanes.grocery.domain.feature.market.model.Market.Brand.Undefined
import com.splanes.grocery.domain.feature.market.model.Market.Carrefour
import com.splanes.grocery.domain.feature.market.model.Market.Condis
import com.splanes.grocery.domain.feature.market.model.Market.Consum
import com.splanes.grocery.domain.feature.market.model.Market.CorteIngles
import com.splanes.grocery.domain.feature.market.model.Market.Dia
import com.splanes.grocery.domain.feature.market.model.Market.Esclat
import com.splanes.grocery.domain.feature.market.model.Market.GranjaArmengol
import com.splanes.grocery.domain.feature.market.model.Market.Lidl
import com.splanes.grocery.domain.feature.market.model.Market.Mercadona
import com.splanes.grocery.domain.feature.market.model.Market.OrigenAmetller
import com.splanes.grocery.domain.feature.market.model.Market.Taranna
import com.splanes.grocery.domain.feature.market.model.Market.Xarcuteca
import com.splanes.grocery.ui.utils.resources.Drawables

fun Market.Brand.logoResId(fallback: Int = Drawables.ic_emoji_unknown_1): Int =
    when (this) {
        Bonpreu -> Drawables.ic_market_brand_bonpreu
        Bosch -> Drawables.ic_market_brand_bosch
        Carrefour -> Drawables.ic_market_brand_carrefour
        Condis -> Drawables.ic_market_brand_condis
        Consum -> Drawables.ic_market_brand_consum
        CorteIngles -> Drawables.ic_market_brand_corte_ingles
        Dia -> Drawables.ic_market_brand_dia
        Esclat -> Drawables.ic_market_brand_esclat
        GranjaArmengol -> Drawables.ic_market_brand_granja_armengol
        Lidl -> Drawables.ic_market_brand_lidl
        Mercadona -> Drawables.ic_market_brand_mercadona
        OrigenAmetller -> Drawables.ic_market_brand_origen_ametller
        Taranna -> Drawables.ic_market_brand_taranna
        Xarcuteca -> Drawables.ic_market_brand_xarcuteca
        Undefined -> fallback
    }