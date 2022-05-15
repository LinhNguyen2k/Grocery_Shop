package com.example.grocery_shop.model.order

data class OrderDetailEntity(
    val amount: Int,
    val delete: Boolean,
    val id: IdX,
    val isReview: String,
    val quantity: Int
)