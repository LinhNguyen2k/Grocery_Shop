package com.example.grocery_shop.model.auth

import com.google.gson.annotations.SerializedName

data class bodyLoginGithub(@SerializedName("status") val  status : Int, @SerializedName("message") val message : String )