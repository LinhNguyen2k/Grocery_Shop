package com.example.grocery_shop.model.cart

import com.google.gson.annotations.SerializedName

data class CartBody (@SerializedName("productId") val productId : String? = null,
                    @SerializedName("quantity") val quantity: Int? = null,
                    @SerializedName("userId") val userId : String? = null)