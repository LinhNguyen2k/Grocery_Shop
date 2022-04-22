package com.example.grocery_shop.model.auth

import com.google.gson.annotations.SerializedName

data class LoginBody (@SerializedName("password")val password : String,
                      @SerializedName("username")val username : String )