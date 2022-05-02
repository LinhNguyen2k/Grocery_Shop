package com.example.grocery_shop.model.product

data class productAddBody(
    val descriptionProduct: String,
    val discount: Int,
    val oldUnitPrice: Int,
    val productImage: String,
    val productName: String,
    val quantity: Int,
    val unitPrice: Int
)