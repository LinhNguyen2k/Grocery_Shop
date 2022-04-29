package com.example.grocery_shop.model.order

data class CartEntity(
    val delete: Boolean,
    val id: Id,
    val quantity: Int
)