package com.example.grocery_shop.model.cart

import com.google.gson.annotations.SerializedName

data class deleteCartBody(@SerializedName("productId") val productId : String? = null,
                            @SerializedName("userId") val userId : String? = null)
