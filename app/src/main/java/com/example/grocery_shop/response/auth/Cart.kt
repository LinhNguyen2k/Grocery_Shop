package com.example.grocery_shop.response.auth

data class Cart(
    val delete: Boolean,
    val id: Id,
    val quantity: Int
)