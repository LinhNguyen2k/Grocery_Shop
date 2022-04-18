package com.example.grocery_shop.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.grocery_shop.base.BaseViewModel
import com.example.grocery_shop.model.SignBody
import com.example.grocery_shop.repository.LoginRepository
import com.example.grocery_shop.response.LoginResponse

class AuthenticationViewModel : BaseViewModel() {
    private val authenticationRepository by lazy {
        LoginRepository()
    }

     val resultSignUp by lazy {
        MutableLiveData<LoginResponse>()
    }


    fun signUp(avatar : String? = null, email : String,  fullName : String, phone : String,  username : String){
        val signBody = SignBody(avatar, email, fullName, phone, username)
        launchHandler {
            authenticationRepository.signUpWithAccount(signBody).subscribe { signBody ->
                resultSignUp.postValue(signBody)
            }

        }
    }
}