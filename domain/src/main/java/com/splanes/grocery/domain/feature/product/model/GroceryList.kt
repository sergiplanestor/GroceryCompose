package com.splanes.grocery.domain.feature.product.model

data class GroceryList(
    val id: String,
    //val color: GroceryList.Color?,
    //val thumbnail: String?,
    val name: String,
    val description: String?,
    val items: Item?,
    val price: String?,
    //val type: GroceryList.Type,
    val isPinned: Boolean,
    val isFavorite: Boolean,
    //val sharedWith: List<UserShared>,
    val createdBy: String,
    val createdOn: Long,
    val updatedBy: String,
    val updatedOn: Long,
) {
    data class Item(
        val id: String,
        val belongsTo: List<String>,
        val amount: Amount,
    )

    data class Amount(
        val total: Double,
        val pending: Double,
    )
}
