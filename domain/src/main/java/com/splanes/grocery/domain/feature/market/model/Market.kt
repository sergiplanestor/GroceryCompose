package com.splanes.grocery.domain.feature.market.model

data class Market(
    val id: String,
    val name: String,
    val description: String = "",
    val brand: Brand = Brand.Undefined,
    val interest: Interest = Interest.Undefined,
    val distance: Distance = Distance.Undefined,
    val qualification: Qualification = Qualification.Undefined,
) {

    enum class Category {
        Supermarket,
        Charcuterie,
        Delicatessen,
        Other,
        Undefined
    }

    sealed class Brand(open val id: String, open val name: String, open val categories: List<Category>) {
        object Undefined : Brand(id = MARKET_BRAND_ID_UNDEFINED, name = "", categories = emptyList())
    }
    object Condis : Brand(
        id = MARKET_BRAND_ID_CONDIS,
        name = MARKET_BRAND_NAME_CONDIS,
        categories = listOf(Category.Supermarket)
    )

    object Carrefour : Brand(
        id = MARKET_BRAND_ID_CARREFOUR,
        name = MARKET_BRAND_NAME_CARREFOUR,
        categories = listOf(Category.Supermarket)
    )

    object OrigenAmetller : Brand(
        id = MARKET_BRAND_ID_AMETLLER,
        name = MARKET_BRAND_NAME_AMETLLER,
        categories = listOf(Category.Supermarket, Category.Delicatessen)
    )

    object GranjaArmengol : Brand(
        id = MARKET_BRAND_ID_ARMENGOL,
        name = MARKET_BRAND_NAME_ARMENGOL,
        categories = listOf(Category.Charcuterie, Category.Delicatessen)
    )

    object Dia : Brand(
        id = MARKET_BRAND_ID_DIA,
        name = MARKET_BRAND_NAME_DIA,
        categories = listOf(Category.Supermarket)
    )

    object Bonpreu : Brand(
        id = MARKET_BRAND_ID_BONPREU,
        name = MARKET_BRAND_NAME_BONPREU,
        categories = listOf(Category.Supermarket)
    )

    object Esclat : Brand(
        id = MARKET_BRAND_ID_ESCLAT,
        name = MARKET_BRAND_NAME_ESCLAT,
        categories = listOf(Category.Supermarket)
    )

    object Lidl : Brand(
        id = MARKET_BRAND_ID_LIDL,
        name = MARKET_BRAND_NAME_LIDL,
        categories = listOf(Category.Supermarket)
    )

    object Mercadona : Brand(
        id = MARKET_BRAND_ID_MERCADONA,
        name = MARKET_BRAND_NAME_MERCADONA,
        categories = listOf(Category.Supermarket)
    )

    object Consum : Brand(
        id = MARKET_BRAND_ID_CONSUM,
        name = MARKET_BRAND_NAME_CONSUM,
        categories = listOf(Category.Supermarket)
    )

    object Bosch : Brand(
        id = MARKET_BRAND_ID_BOSCH,
        name = MARKET_BRAND_NAME_BOSCH,
        categories = listOf(Category.Charcuterie)
    )

    object Xarcuteca : Brand(
        id = MARKET_BRAND_ID_XARCUTECA,
        name = MARKET_BRAND_NAME_XARCUTECA,
        categories = listOf(Category.Charcuterie, Category.Delicatessen)
    )

    object Taranna : Brand(
        id = MARKET_BRAND_ID_TARANNA,
        name = MARKET_BRAND_NAME_TARANNA,
        categories = listOf(Category.Charcuterie)
    )

    object CorteIngles : Brand(
        id = MARKET_BRAND_ID_CORTE_INGLES,
        name = MARKET_BRAND_NAME_CORTE_INGLES,
        categories = listOf(Category.Supermarket, Category.Delicatessen)
    )

    data class Interest(val id: String, val weight: InterestWeight) {
        companion object
    }

    enum class InterestWeight { Undefined, Low, Mid, High, Indifferent, LowTested, Increasing, Decreasing }

    data class Distance(val id: String) {
        companion object
    }

    data class Qualification(val id: String, val weight: QualificationWeight) {
        companion object
    }

    enum class QualificationWeight(val value: Int) {
        Undefined(value = -1), Awful(value = 0), Bad(value = 0), Normal(value = 1), Good(value = 2), Excellent(value = 3)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? Market)?.id == id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + interest.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + qualification.hashCode()
        return result
    }
}

val Market.Interest.Companion.Undefined: Market.Interest by lazy {
    Market.Interest(
        id = MARKET_INTEREST_UNDEFINED,
        weight = Market.InterestWeight.Undefined
    )
}

val Market.Distance.Companion.Undefined: Market.Distance by lazy {
    Market.Distance(id = MARKET_DISTANCE_UNDEFINED)
}

val Market.Qualification.Companion.Undefined: Market.Qualification by lazy {
    Market.Qualification(
        id = MARKET_QUALIFICATION_UNDEFINED,
        weight = Market.QualificationWeight.Undefined
    )
}

private const val MARKET_BRAND_ID_UNDEFINED = "MARKET.BRAND_UNKNOWN"
private const val MARKET_BRAND_ID_CONDIS = "MARKET.BRAND_CONDIS"
private const val MARKET_BRAND_ID_CARREFOUR = "MARKET.BRAND_CARREFOUR"
private const val MARKET_BRAND_ID_AMETLLER = "MARKET.BRAND_AMETLLER"
private const val MARKET_BRAND_ID_ARMENGOL = "MARKET.BRAND_ARMENGOL"
private const val MARKET_BRAND_ID_DIA = "MARKET.BRAND_DIA"
private const val MARKET_BRAND_ID_BONPREU = "MARKET.BRAND_BONPREU"
private const val MARKET_BRAND_ID_ESCLAT = "MARKET.BRAND_ESCLAT"
private const val MARKET_BRAND_ID_LIDL = "MARKET.BRAND_LIDL"
private const val MARKET_BRAND_ID_MERCADONA = "MARKET.BRAND_MERCADONA"
private const val MARKET_BRAND_ID_CONSUM = "MARKET.BRAND_CONSUM"
private const val MARKET_BRAND_ID_BOSCH = "MARKET.BRAND_BOSCH"
private const val MARKET_BRAND_ID_XARCUTECA = "MARKET.BRAND_XARCUTECA"
private const val MARKET_BRAND_ID_TARANNA = "MARKET.BRAND_TARANNA"
private const val MARKET_BRAND_ID_CORTE_INGLES = "MARKET.BRAND_CORTE_INGLES"

private const val MARKET_BRAND_NAME_CONDIS = "Condis"
private const val MARKET_BRAND_NAME_CARREFOUR = "Carrefour"
private const val MARKET_BRAND_NAME_AMETLLER = "Origen Ametller"
private const val MARKET_BRAND_NAME_ARMENGOL = "Granja Armengol"
private const val MARKET_BRAND_NAME_DIA = "Dia"
private const val MARKET_BRAND_NAME_BONPREU = "Bonpreu"
private const val MARKET_BRAND_NAME_ESCLAT = "Esclat"
private const val MARKET_BRAND_NAME_LIDL = "Lidl"
private const val MARKET_BRAND_NAME_MERCADONA = "Mercadona"
private const val MARKET_BRAND_NAME_CONSUM = "Consum"
private const val MARKET_BRAND_NAME_BOSCH = "Bosch"
private const val MARKET_BRAND_NAME_XARCUTECA = "Xarcuteca"
private const val MARKET_BRAND_NAME_TARANNA = "Taranna"
private const val MARKET_BRAND_NAME_CORTE_INGLES = "Corte Ingles"

private const val MARKET_INTEREST_UNDEFINED = "MARKET.INTEREST_UNKNOWN"
private const val MARKET_DISTANCE_UNDEFINED = "MARKET.DISTANCE_UNKNOWN"
private const val MARKET_QUALIFICATION_UNDEFINED = "MARKET.QUALIFICATION_UNKNOWN"