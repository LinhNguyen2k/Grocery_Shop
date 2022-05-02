package com.example.grocery_shop.view.activity.client

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.databinding.ActivitySignUpBinding
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class SignUpActivity : BaseVMActivity<ActivitySignUpBinding, AuthenticationViewModel>() {
    private val authenticationViewModel by lazy {
        ViewModelProvider(this)[AuthenticationViewModel::class.java]
    }
    override fun initView() {
    }

    override fun initListener() {

        binding.btnSignUp.singleClick {
            signUp()
        }

    }

    private fun signUp() {
        authenticationViewModel.signUp(
            null,
            binding.etEmail.text.toString(),
            binding.etFullName.text.toString(),
            binding.etLoginMobileNumber.text.toString(),
            binding.etUserName.text.toString()
        )
    }

    override fun initData() {
        authenticationViewModel.resultSignUp.observe(this, Observer { respones ->
            if (respones.jwt != null) {
                Toast.makeText(applicationContext, "Đăng ký thành công", Toast.LENGTH_LONG).show()
            }
        })
    }

}