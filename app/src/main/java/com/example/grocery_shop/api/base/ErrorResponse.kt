package com.example.grocery_shop.api.base

data class ErrorResponse(
    val msg: String,
    val success: Boolean,
    var isTokenExpire: Boolean
)