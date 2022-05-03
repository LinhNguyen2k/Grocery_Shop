package com.example.grocery_shop.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("status") val status: String? = null,
                         @SerializedName("message") val message: String? = null,
                         @SerializedName("jwt") val jwt : String? = null,
                         @SerializedName("userId") val userId : Long,
                         @SerializedName("username") val username : String? = null,
                         @SerializedName("fullName") val fullName : String? = null,
                         @SerializedName("roleName") val roleName : String? = null)