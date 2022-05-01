package com.example.grocery_shop.model.user

import com.google.gson.annotations.SerializedName

data class UserEditBody(
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null
)

