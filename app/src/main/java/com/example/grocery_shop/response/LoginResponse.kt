package com.example.grocery_shop.response

data class LoginResponse( val status: String? = null, val message: String? = null, val jwt : String? = null,
                            val userId : Long, val username : String? = null, val roleName : String? = null)