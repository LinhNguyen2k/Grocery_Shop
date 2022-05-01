package com.example.grocery_shop.response

import com.google.gson.annotations.SerializedName

data class responseDeleteCart(@SerializedName("status") val status : Boolean? = null)