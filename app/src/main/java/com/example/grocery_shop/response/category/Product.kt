package com.example.grocery_shop.response.category

data class Product(
    val cartEntities: List<Any>,
    val delete: Boolean,
    val descriptionProduct: String,
    val discount: Int,
    val oldUnitPrice: Int,
    val productId: Int,
    val productImage: String,
    val productName: String,
    val quantity: Int,
    val reviewsEntities: List<ReviewsEntity>,
    val unitPrice: Int
)