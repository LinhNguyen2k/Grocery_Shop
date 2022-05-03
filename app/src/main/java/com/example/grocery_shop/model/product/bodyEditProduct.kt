package com.example.grocery_shop.model.product

data class bodyEditProduct(
    val descriptionProduct: String,
    val discount: Int,
    val oldUnitPrice: Int,
    val productImage: String? = null,
    val productName: String,
    val quantity: Int,
    val unitPrice: Int
)