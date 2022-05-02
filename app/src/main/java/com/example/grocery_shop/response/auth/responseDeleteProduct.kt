package com.example.grocery_shop.response.auth

import com.google.gson.annotations.SerializedName

data class responseDeleteProduct (@SerializedName("status") val status : Boolean,
                                  @SerializedName("message") val message : String)