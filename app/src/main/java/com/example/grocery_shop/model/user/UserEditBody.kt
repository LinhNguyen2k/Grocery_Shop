package com.example.grocery_shop.model.user

import com.google.gson.annotations.SerializedName

data class UserEditBody(
    @SerializedName("username") val username: String? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("dateOfBirth") val dateOfBirth: String? = null
)

