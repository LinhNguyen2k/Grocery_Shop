package com.example.grocery_shop.model.order

data class orderBody(
    val address: String,
    val note: String,
    val fullName : String,
    val phoneNumber: String
)