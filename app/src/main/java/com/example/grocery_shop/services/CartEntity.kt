package com.example.grocery_shop.services

data class CartEntity(
    val delete: Boolean,
    val id: Id,
    val quantity: Int
)