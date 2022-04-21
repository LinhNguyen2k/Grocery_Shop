package com.example.grocery_shop.repository

import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.model.AuthenticationRequest
import com.example.grocery_shop.model.SignBody
import com.example.grocery_shop.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository {
    private val apiClient by lazy {
        ApiClient.createService(ProductService::class.java)
    }


   suspend fun signUpWithAccount(request: SignBody): Flow<LoginResponse> {
        return flow { emit(apiClient.signUp(request)) }.flowOn(Dispatchers.IO)
    }

    suspend fun loginUpWithAccount(request: AuthenticationRequest): Flow<LoginResponse> {
        return flow { emit(apiClient.login(request)) }.flowOn(Dispatchers.IO)
    }
}