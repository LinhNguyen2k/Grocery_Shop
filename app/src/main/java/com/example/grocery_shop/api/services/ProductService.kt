package com.example.grocery_shop.api.services

import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.auth.bodyLoginGithub
import com.example.grocery_shop.model.auth.setNewPassBody
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.order.orderBody
import com.example.grocery_shop.model.order.responseOrderByUserId
import com.example.grocery_shop.model.product.bodyEditProduct
import com.example.grocery_shop.model.product.infoProduct
import com.example.grocery_shop.model.product.productAddBody
import com.example.grocery_shop.model.review.bodyReview
import com.example.grocery_shop.model.review.bodyReview_v2Item
import com.example.grocery_shop.model.review.reviewResponseItem
import com.example.grocery_shop.model.user.UserEditBody
import com.example.grocery_shop.model.user.account.accountResponseItem
import com.example.grocery_shop.model.user.infoUser.getUserById
import com.example.grocery_shop.model.user.updateImage.responseImage
import com.example.grocery_shop.model.user.userResponse
import com.example.grocery_shop.response.ForGotPassWordResponse
import com.example.grocery_shop.response.LoginResponse
import com.example.grocery_shop.response.auth.responseDeleteProduct
import com.example.grocery_shop.response.auth.responseNewPass
import com.example.grocery_shop.response.category.responseCategory
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.response.order.orderResponseManage
import com.example.grocery_shop.response.order.responseAllOrder
import com.example.grocery_shop.response.product.responseEditProduct
import com.example.grocery_shop.response.product.responseManageProduct
import com.example.grocery_shop.response.responseDeleteCart
import okhttp3.MultipartBody
import retrofit2.http.*

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

    //categories/detail?id=1
    @GET("/api/v1/categories/detail")
    suspend fun getCategorys(
        @Query("id") id: String? = null,
    ): responseCategory

    @POST("/api/v1/carts")
    suspend fun addProductIntoCart(@Body response: CartBody): CartResponse


    @GET("/api/v1/products/detail")
    suspend fun getInfoProduct(@Query("id") id: String? = null): infoProduct

    @GET("/api/v1/carts/product")
    suspend fun getAllProductCart(@Query("userId") id: String? = null): List<productList>

    @DELETE("/api/v1/carts")
    suspend fun deleteCart(
        @Query("productId") productId: String,
        @Query("userId") userId: String
    ): responseDeleteCart


    @POST("/api/v1/users/info")
    suspend fun editProfile(
        @Query("id") id: String? = null,
        @Body response: UserEditBody
    ): userResponse

    @Multipart
    @POST("/api/v1/users/change-avt")
    suspend fun editProfileImage(
        @Query("id") id: String? = null,
        @Part avt: MultipartBody.Part
    ): responseImage

    @GET("/api/v1/users/detail")
    suspend fun getInfoUser(@Query("id") id: String? = null): getUserById

    @POST("/api/v1/auth/new-password")
    suspend fun setNewPassWord(@Body response: setNewPassBody): responseNewPass

    @POST("/api/v1/orders")
    suspend fun orderClient(
        @Query("userId") userId: String? = null,
        @Body response: orderBody
    ): orderResponse

    @GET("/api/v1/products/search")
    suspend fun getListSearch(@Query("q") q: String? = null): List<productList>

    @DELETE("/api/v1/products")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Query("id") id: String? = null
    ): responseDeleteProduct

    @POST("/api/v1/products")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Query("cateId") id: String? = null,
        @Body response: productAddBody
    ): responseManageProduct

    @Multipart
    @POST("/api/v1/products/avt")
    suspend fun addImage(
        @Header("Authorization") token: String,
        @Query("id") id: String? = null,
        @Part avt: MultipartBody.Part
    ): responseManageProduct

    @GET("/api/v1/orders")
    suspend fun getListOrder(): List<orderResponseManage>

    @DELETE("/api/v1/orders")
    suspend fun deleteOrder(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): responseDeleteProduct

    @GET("/api/v1/users")
    suspend fun getAllAccount(@Header("Authorization") token: String): List<accountResponseItem>

    @GET("/api/v1/orders/year-revenue")
    suspend fun getAllOrder(
        @Header("Authorization") token: String,
        @Query("year") year: String,
        @Query("categoryId") categoryId: String
    ): responseAllOrder

    @GET("/oauth2/authorization/github")
    suspend fun getTokenGithub(): Unit

    @GET("/api/v1/github/save")
    suspend fun saveUserWhenLoginGithub(): bodyLoginGithub

    @GET("/api/v1/orders/user")
    suspend fun getOrderById(@Query("userId") userId: String): responseOrderByUserId

    @GET("/api/v1/reviews/product")
    suspend fun getReviewByProductId(@Query("productId") productId: String): List<bodyReview_v2Item>

    @POST("/api/v1/products/change-info")
    suspend fun editInfoProduct(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Body response: bodyEditProduct
    ): responseEditProduct

    @POST("/api/v1/reviews")
    suspend fun postReview(@Body response: bodyReview): bodyReview_v2Item
}
