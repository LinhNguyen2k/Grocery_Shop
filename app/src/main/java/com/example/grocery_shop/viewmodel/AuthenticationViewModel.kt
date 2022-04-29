package com.example.grocery_shop.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.grocery_shop.api.base.ErrorResponse
import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.base.BaseViewModel
import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.product.infoProduct
import com.example.grocery_shop.repository.LoginRepository
import com.example.grocery_shop.response.CartGetAllResponseItem
import com.example.grocery_shop.response.ForGotPassWordResponse
import com.example.grocery_shop.response.LoginResponse

class AuthenticationViewModel : BaseViewModel() {
    private val authenticationRepository by lazy {
        LoginRepository()
    }
    private val apiClient by lazy {
        ApiClient.createService(ProductService::class.java)
    }
    val resultSignUp by lazy {
        MutableLiveData<LoginResponse>()
    }

    val resultAddCart by lazy {
        MutableLiveData<CartResponse>()
    }

    fun signUp(
        avatar: String? = null,
        email: String,
        fullName: String,
        phone: String,
        username: String
    ) {
        val signBody = SignBody(avatar, email, fullName, phone, username)
        launchHandler {
            authenticationRepository.signUpWithAccount(signBody).subscribe(onNext = { response ->
                resultSignUp.postValue(response)
            }, onError = { error ->
                error?.let { response ->
                    Toast.makeText(
                        context,
                        "${response.status}  ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })
        }
    }

    fun login(
        password: String, username: String, onComplete: (response: LoginResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        val loginBody = LoginBody(password, username)
        launchHandler {
            authenticationRepository.loginUpWithAccount(loginBody).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })

        }

    }

    fun forGotPassWord(
        username: String,
        onComplete: (response: ForGotPassWordResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.forGotPassWord(username).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })

        }
    }

    fun getCategory(
        page: String? = null,
        category: String? = null,
        onComplete: (response: List<productList>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = true
        launchHandler {
            authenticationRepository.getCategory(page, category).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun getCategoryDetail(
        page: String? = null,
        category: String? = null,
        onComplete: (response: List<productList>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = false
        launchHandler {
            authenticationRepository.getCategory(page, category).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun addProductIntoCart(
        request: CartBody,
//        onComplete: (response: CartResponse) -> Unit,
//        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = false
        launchHandler {
            authenticationRepository.addProductIntoCart(request)
                .subscribe{ cartResponse ->
                    resultAddCart.postValue(cartResponse)
                }
        }
    }

    fun getAllProductCart(
        userId: String,
        onComplete: (response: List<CartGetAllResponseItem>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.getAllProductCart(userId).subscribe(onNext = { cartResponse ->
                onComplete.invoke(cartResponse)

            }, onError = {err ->
                onErrors?.invoke(err)
            })
        }

    }

//    fun getInfoProduct(
//        id: String?,
//        onComplete: (response: infoProduct) -> Unit
//    ) {
//        isLoading.value = true
//        launchHandler {
//            flowOnIO(apiClient.getInfoProduct(id)).subscribe { response ->
//                onComplete.invoke(response)
//                isLoading.value = false
//            }
//        }
//    }
}