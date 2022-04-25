package com.example.grocery_shop.model.product

data class infoProduct(
    val cartEntities: List<Any>,
    val delete: Boolean,
    val descriptionProduct: String,
    val discount: Int,
    val oldUnitPrice: Any,
    val productId: Int,
    val productImage: String,
    val productName: String,
    val quantity: Int,
    val reviewsEntities: List<Any>,
    val unitPrice: Int
)