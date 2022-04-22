package com.example.grocery_shop.api.base

data class ErrorResponse(
    val timestamp: String?,
    val status: String?,
    val error: String?,
    val path: String?,
)