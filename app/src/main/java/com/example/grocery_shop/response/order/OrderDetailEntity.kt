package com.example.grocery_shop.response.order

data class OrderDetailEntity(
    val amount: Int,
    val delete: Boolean,
    val id: Id,
    val isReview: String,
    val quantity: Int
)