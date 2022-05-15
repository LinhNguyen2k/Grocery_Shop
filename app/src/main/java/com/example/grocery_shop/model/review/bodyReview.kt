package com.example.grocery_shop.model.review

data class bodyReview(
    val comments: String,
    val orderId: Int,
    val productId: Int,
    val rating: Int,
    val usedId: Int
)