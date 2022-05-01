package com.example.grocery_shop.repository

import android.icu.text.MessagePattern
import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.auth.setNewPassBody
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.cart.deleteCartBody
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.order.orderBody
import com.example.grocery_shop.model.user.UserEditBody
import com.example.grocery_shop.model.user.infoUser.getUserById
import com.example.grocery_shop.model.user.userResponse
import com.example.grocery_shop.response.CartGetAllResponseItem
import com.example.grocery_shop.response.ForGotPassWordResponse
import com.example.grocery_shop.response.LoginResponse
import com.example.grocery_shop.response.auth.responseNewPass
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.response.responseDeleteCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody

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
  suspend fun deleteCart(
      productId: String,
    userId: String
    ): Flow<responseDeleteCart> {
        return flow { emit(apiClient.deleteCart(productId, userId)) }.flowOn(Dispatchers.IO)
    }

  suspend fun getInfoUser(
      id: String,
    ): Flow<getUserById> {
        return flow { emit(apiClient.getInfoUser(id)) }.flowOn(Dispatchers.IO)
    }

  suspend fun editProfile(
      id: String,
      request: UserEditBody
    ): Flow<userResponse> {
        return flow { emit(apiClient.editProfile(id, request)) }.flowOn(Dispatchers.IO)
    }


  suspend fun setNewPassWord(
      request: setNewPassBody
    ): Flow<responseNewPass> {
        return flow { emit(apiClient.setNewPassWord(request)) }.flowOn(Dispatchers.IO)
    }


  suspend fun orderClient(
      id: String,
      request: orderBody
    ): Flow<orderResponse> {
        return flow { emit(apiClient.orderClient(id, request)) }.flowOn(Dispatchers.IO)
    }


  suspend fun getListSearch(
      key : String
    ): Flow<List<productList>> {
        return flow { emit(apiClient.getListSearch(key)) }.flowOn(Dispatchers.IO)
    }



}