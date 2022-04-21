package com.example.grocery_shop.model

import com.google.gson.annotations.SerializedName

data class AuthenticationRequest (@SerializedName("password")val password : String,
                                  @SerializedName("username")val username : String )