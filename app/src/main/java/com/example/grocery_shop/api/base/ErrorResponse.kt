package com.example.grocery_shop.api.base

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ErrorResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null
)