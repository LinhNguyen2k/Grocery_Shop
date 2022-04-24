package com.example.grocery_shop.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.base.BaseViewModel
import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.repository.LoginRepository
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

    val resultCategory by lazy {
        MutableLiveData<ArrayList<productList>>()
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
                        "${response.status}  ${response.error}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })
        }
    }

    fun login(password: String, username: String, onComplete: (response: LoginResponse) -> Unit) {
        val loginBody = LoginBody(password, username)
        launchHandler {
            flowOnIO(apiClient.login(loginBody)).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                err?.let { response ->
                    Toast.makeText(
                        context,
                        "${response.status}  ${response.error}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })

        }

    }

    fun forGotPassWord(
        username: String,
        onComplete: (response: ForGotPassWordResponse) -> Unit
    ) {
        launchHandler {
            flowOnIO(apiClient.forgotPassWord(username)).subscribe { response ->
                onComplete.invoke(response)
            }

        }
    }

    //alo, khong nghe, co nghe
    fun getCategory(
        page: Int? = null,
        category: Int? = null,
        onComplete: (response: List<productList>) -> Unit
    ) {
        isLoading.value = true
        launchHandler {
            flowOnIO(apiClient.getCategory(page, category)).subscribe { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }
        }
    }
}