package com.example.grocery_shop.model.user.account

data class accountResponseItem(
    val authProvider: Any,
    val avatar: String,
    val cart: List<Any>,
    val dateOfBirthday: String,
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