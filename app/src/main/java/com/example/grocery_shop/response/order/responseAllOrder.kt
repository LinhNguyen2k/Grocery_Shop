package com.example.grocery_shop.response.order

data class responseAllOrder(
    val products: List<Product>,
    val totalMoney: Int
)