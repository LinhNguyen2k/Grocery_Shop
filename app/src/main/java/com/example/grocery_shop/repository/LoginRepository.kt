package com.example.grocery_shop.repository

import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.response.CartGetAllResponseItem
import com.example.grocery_shop.response.ForGotPassWordResponse
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

    suspend fun loginUpWithAccount(request: LoginBody): Flow<LoginResponse> {
        return flow { emit(apiClient.login(request)) }.flowOn(Dispatchers.IO)
    }

    suspend fun forGotPassWord(username: String): Flow<ForGotPassWordResponse> {
        return flow { emit(apiClient.forgotPassWord(username)) }.flowOn(Dispatchers.IO)
    }

    suspend fun getCategory(
        page: String? = null,
        category: String? = null
    ): Flow<List<productList>> {
        return flow { emit(apiClient.getCategory(page, category)) }.flowOn(Dispatchers.IO)
    }
    suspend fun addProductIntoCart(
        request: CartBody
    ): Flow<CartResponse> {
        return flow { emit(apiClient.addProductIntoCart(request)) }.flowOn(Dispatchers.IO)
    }
   suspend fun getAllProductCart(
        userId: String
    ): Flow<List<productList>> {
        return flow { emit(apiClient.getAllProductCart(userId)) }.flowOn(Dispatchers.IO)
    }


}