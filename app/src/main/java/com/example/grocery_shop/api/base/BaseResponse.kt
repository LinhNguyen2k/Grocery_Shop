package com.example.grocery_shop.api.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse<T>  {
    @Expose
    @SerializedName("success")
    val status: Boolean = false

    @Expose
    @SerializedName("result")
    val data: T? = null

    @Expose
    @SerializedName("msg")
    val message: String? = null
}