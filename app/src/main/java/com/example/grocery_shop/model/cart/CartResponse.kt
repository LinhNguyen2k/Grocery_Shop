package com.example.grocery_shop.model.cart

data class CartResponse(val delete: Boolean,
                        val id: Id,
                        val quantity: Int)