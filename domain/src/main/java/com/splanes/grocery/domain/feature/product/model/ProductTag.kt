package com.splanes.grocery.domain.feature.product.model

data class ProductTag(val id: String, val text: String, val type: Type) {
    enum class Type {
        // Flavor
        YumiYumi,
        NastyButNeeded,

        // Freq
        Recurrent,
        Barely,

        // Misc
        RequiredByNaima,

        // Custom
        Custom,
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as? ProductTag)?.id == id
    }
}
