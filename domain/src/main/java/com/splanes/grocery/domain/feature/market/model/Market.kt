package com.splanes.grocery.domain.feature.market.model

data class Market(
    val id: String,
    val name: String,
    val interest: MarketInterest = MarketInterest.Undefined,
    val distance: MarketDistance = MarketDistance.Undefined,
    val qualification: MarketQualification = MarketQualification.Undefined,
) {

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

data class MarketInterest(
    val id: String,
    val value: Value
) {
    enum class Value {
        Low,
        Mid,
        High,
        Undefined,
        Indifferent,
        LowTested,
        Increasing,
        Decreasing,
        Custom,
    }
    companion object {
        val Undefined: MarketInterest by lazy { MarketInterest(MARKET_INTEREST_UNDEFINED, value = Value.Undefined) }
    }
}

data class MarketDistance(
    val id: String,
) {

    companion object {
        val Undefined: MarketDistance by lazy { MarketDistance(MARKET_DISTANCE_UNDEFINED) }
    }
}

data class MarketQualification(
    val id: String,
) {

    companion object {
        val Undefined: MarketQualification by lazy { MarketQualification(MARKET_QUALIFICATION_UNDEFINED) }
    }
}

private const val MARKET_INTEREST_UNDEFINED = "MARKET.INTEREST_UNKNOWN"
private const val MARKET_DISTANCE_UNDEFINED = "MARKET.DISTANCE_UNKNOWN"
private const val MARKET_QUALIFICATION_UNDEFINED = "MARKET.QUALIFICATION_UNKNOWN"