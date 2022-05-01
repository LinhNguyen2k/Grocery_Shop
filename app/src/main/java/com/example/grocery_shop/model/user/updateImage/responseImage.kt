package com.example.grocery_shop.model.user.updateImage

data class responseImage(
    val authProvider: Any,
    val avatar: String,
    val cart: List<Cart>,
    val dateOfBirthday: Any,
    val delete: Boolean,
    val email: String,
    val fullName: String,
    val orders: List<Any>,
    val password: String,
    val phone: String,
    val reviews: List<Any>,
    val role: Role,
    val userId: Int,
    val username: String
)