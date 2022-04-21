package com.example.grocery_shop.api.services

import com.example.grocery_shop.model.AuthenticationRequest
import com.example.grocery_shop.model.SignBody
import com.example.grocery_shop.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductService {

    @POST("/api/v1/auth/signup")
    suspend fun signUp(
        @Body request: SignBody
    ): LoginResponse


    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: AuthenticationRequest
    ): LoginResponse
}