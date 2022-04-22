package com.example.grocery_shop.model.auth

import com.google.gson.annotations.SerializedName

data class SetNewPassWordBody(@SerializedName("username") val username : String? = null,
                                @SerializedName("token") val token : String? = null,
                                @SerializedName("newPassword") val newPassWord : String? = null)