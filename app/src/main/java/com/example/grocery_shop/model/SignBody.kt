package com.example.grocery_shop.model

import com.google.gson.annotations.SerializedName

data class SignBody(
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("email") val email: String,
    @SerializedName("fullName")val fullName: String,
    @SerializedName("phone")val phone: String,
    @SerializedName("username")val username: String
)
