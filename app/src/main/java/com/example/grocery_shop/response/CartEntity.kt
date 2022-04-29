package com.example.grocery_shop.response

data class CartEntity(
    val delete: Boolean,
    val id: Id,
    val quantity: Int
)