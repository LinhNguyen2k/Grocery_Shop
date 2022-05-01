package com.example.grocery_shop.model.auth

data class setNewPassBody(
    val newPassword: String,
    val token: String,
    val username: String
)