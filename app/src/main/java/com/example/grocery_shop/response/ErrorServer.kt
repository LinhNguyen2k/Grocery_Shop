package com.example.grocery_shop.response

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ErrorServer(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("message")
    val message: String?
)