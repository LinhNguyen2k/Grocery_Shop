package com.example.grocery_shop.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.base.BaseViewModel
import com.example.grocery_shop.model.AuthenticationRequest
import com.example.grocery_shop.model.SignBody
import com.example.grocery_shop.repository.LoginRepository
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

    val resultLogin by lazy {
        MutableLiveData<LoginResponse>()
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
            authenticationRepository.signUpWithAccount(signBody).subscribe { signBody ->
                resultSignUp.postValue(signBody)
            }

        }
    }

    fun login(password: String, username: String) {
        val loginBody = AuthenticationRequest(password, username)
        launchHandler {
            authenticationRepository.loginUpWithAccount(loginBody).subscribe { loginBody ->
                resultLogin.postValue(loginBody)
            }

        }
    }
}