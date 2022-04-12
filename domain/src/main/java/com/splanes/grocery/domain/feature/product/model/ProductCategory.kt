package com.splanes.grocery.domain.feature.product.model

data class ProductCategory(
    val id: String,
    val type: Type
) {
    enum class Type {
        Undefined,
        Fruits,
        Vegetables,
        Meat,
        Fish,
        CuredSausages,
        Dairy,
        Pasta,
        Cookies,
        Cleaning,
        Other,
        Custom
    }

    companion object {
        val Undefined by lazy { ProductCategory(id = PRODUCT_CATEGORY_UNDEFINED, type = Type.Undefined) }
    }
}

private const val PRODUCT_CATEGORY_UNDEFINED = "PRODUCT.CATEGORY_UNKNOWN"
private const val PRODUCT_CATEGORY_FRUITS = "PRODUCT.CATEGORY_FRUITS"
private const val PRODUCT_CATEGORY_VEGETABLES = "PRODUCT.CATEGORY_VEGETABLES"
private const val PRODUCT_CATEGORY_MEAT = "PRODUCT.CATEGORY_MEAT"
private const val PRODUCT_CATEGORY_FISH = "PRODUCT.CATEGORY_FISH"
private const val PRODUCT_CATEGORY_CURED_SAUSAGE = "PRODUCT.CATEGORY_CURED_SAUSAGE"
private const val PRODUCT_CATEGORY_DAIRY = "PRODUCT.CATEGORY_DAIRY"
private const val PRODUCT_CATEGORY_PASTA = "PRODUCT.CATEGORY_PASTA"
private const val PRODUCT_CATEGORY_COOKIES = "PRODUCT.CATEGORY_COOKIES"
private const val PRODUCT_CATEGORY_CLEANING = "PRODUCT.CATEGORY_CLEANING"