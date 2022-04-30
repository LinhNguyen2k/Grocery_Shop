package com.example.grocery_shop.api.services

import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.product.infoProduct
import com.example.grocery_shop.response.CartGetAllResponseItem
import com.example.grocery_shop.response.ForGotPassWordResponse
import com.example.grocery_shop.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @POST("/api/v1/auth/signup")
    suspend fun signUp(
        @Body request: SignBody
    ): LoginResponse


    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: LoginBody
    ): LoginResponse

    @GET("/api/v1/auth/generate-token-pass")
    suspend fun forgotPassWord(@Query("username") username: String?): ForGotPassWordResponse

    @GET("/api/v1/products")
    suspend fun getCategory(
        @Query("page") page: String? = null,
        @Query("category") category: String? = null
    ): List<productList>

    @POST("/api/v1/carts")
    suspend fun addProductIntoCart(@Body response: CartBody) : CartResponse


    @GET("/api/v1/products/detail")
    suspend fun getInfoProduct(@Query("id") id: String? = null): infoProduct

    @GET("/api/v1/carts/product")
    suspend fun getAllProductCart(@Query("userId") id: String? = null): List<productList>
}
