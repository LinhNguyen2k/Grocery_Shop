package com.example.grocery_shop.repository

import android.icu.text.MessagePattern
import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.model.auth.*
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.cart.deleteCartBody
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.order.orderBody
import com.example.grocery_shop.model.order.responseOrderByUserId
import com.example.grocery_shop.model.order.responseOrderByUserIdItem
import com.example.grocery_shop.model.product.bodyEditProduct
import com.example.grocery_shop.model.product.productAddBody
import com.example.grocery_shop.model.review.bodyReview
import com.example.grocery_shop.model.review.bodyReview_v2Item
import com.example.grocery_shop.model.review.reviewResponse
import com.example.grocery_shop.model.review.reviewResponseItem
import com.example.grocery_shop.model.user.UserEditBody
import com.example.grocery_shop.model.user.account.accountResponseItem
import com.example.grocery_shop.model.user.infoUser.getUserById
import com.example.grocery_shop.model.user.userResponse
import com.example.grocery_shop.response.*
import com.example.grocery_shop.response.auth.responseDeleteProduct
import com.example.grocery_shop.response.auth.responseNewPass
import com.example.grocery_shop.response.category.responseCategory
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.response.order.orderResponseManage
import com.example.grocery_shop.response.order.responseAllOrder
import com.example.grocery_shop.response.product.responseEditProduct
import com.example.grocery_shop.response.product.responseManageProduct
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
        key: String
    ): Flow<List<productList>> {
        return flow { emit(apiClient.getListSearch(key)) }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteProduct(
        token: String,
        key: String
    ): Flow<responseDeleteProduct> {
        return flow { emit(apiClient.deleteProduct(token, key)) }.flowOn(Dispatchers.IO)
    }

    suspend fun addProduct(
        token: String,
        key: String,
        body: productAddBody
    ): Flow<responseManageProduct> {
        return flow { emit(apiClient.addProduct(token, key, body)) }.flowOn(Dispatchers.IO)
    }


    suspend fun getListOrder(
    ): Flow<List<orderResponseManage>> {
        return flow { emit(apiClient.getListOrder()) }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteOrder(
        token: String,
        key: String
    ): Flow<responseDeleteProduct> {
        return flow { emit(apiClient.deleteOrder(token, key)) }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllAccount(
        token: String
    ): Flow<List<accountResponseItem>> {
        return flow { emit(apiClient.getAllAccount(token)) }.flowOn(Dispatchers.IO)
    }

    suspend fun editInfoProduct(
        token: String,
        id: String,
        body: bodyEditProduct
    ): Flow<responseEditProduct> {
        return flow { emit(apiClient.editInfoProduct(token, id, body)) }.flowOn(Dispatchers.IO)
    }

    suspend fun postReview(
        body: bodyReview
    ): Flow<bodyReview_v2Item> {
        return flow { emit(apiClient.postReview(body)) }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllOrder(
        token: String,
        year: String,
        category: String?
    ): Flow<responseAllOrder> {
        return flow { emit(apiClient.getAllOrder(token, year, category.toString())) }.flowOn(
            Dispatchers.IO
        )
    }

    suspend fun getTokenGithub(

    ): Flow<Unit> {
        return flow { emit(apiClient.getTokenGithub()) }.flowOn(Dispatchers.IO)
    }

    suspend fun saveUserWhenLoginGithub(
    ): Flow<bodyLoginGithub> {
        return flow { emit(apiClient.saveUserWhenLoginGithub()) }.flowOn(Dispatchers.IO)
    }

    suspend fun getOrderById(
        userId: String
    ): Flow<responseOrderByUserId> {
        return flow { emit(apiClient.getOrderById(userId)) }.flowOn(Dispatchers.IO)
    }

    suspend fun getReviewByProductId(
        userId: String
    ): Flow<List<bodyReview_v2Item>> {
        return flow { emit(apiClient.getReviewByProductId(userId)) }.flowOn(Dispatchers.IO)
    }

    suspend fun getCategorys(
        id: String
    ): Flow<responseCategory> {
        return flow { emit(apiClient.getCategorys(id)) }.flowOn(Dispatchers.IO)
    }


}