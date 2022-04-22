package com.example.grocery_shop.response

import com.google.gson.annotations.SerializedName

data class ForGotPassWordResponse(@SerializedName("status") val status : Boolean, @SerializedName("message") val message : String? = null)