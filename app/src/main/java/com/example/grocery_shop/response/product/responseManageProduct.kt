package com.example.grocery_shop.response.product

data class responseManageProduct(
    val cartEntities: Any,
    val delete: Boolean,
    val descriptionProduct: String,
    val discount: Int,
    val oldUnitPrice: Int,
    val productId: Int,
    val productImage: String,
    val productName: String,
    val quantity: Int,
    val reviewsEntities: Any,
    val unitPrice: Int
)
