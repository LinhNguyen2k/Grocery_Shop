package com.example.grocery_shop.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseActivity
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.databinding.ActivitySignUpBinding
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity
import kotlinx.coroutines.delay

class SignUpActivity : BaseVMActivity<ActivitySignUpBinding, AuthenticationViewModel>() {
    private val authenticationViewModel by lazy {
        ViewModelProvider(this)[AuthenticationViewModel::class.java]
    }

    override fun initView() {
    }

    override fun initListener() {

        binding.btnSignUp.singleClick {
            lifecycleScope.launchWhenCreated {
                authenticationViewModel.signUp(null,
                    binding.etEmail.text.toString(),binding.etFullName.text.toString(), binding.etLoginMobileNumber.text.toString(),
                    binding.etUserName.text.toString()
                )
            }
        }



    }

    override fun initData() {
        authenticationViewModel.resultSignUp.observe(this, Observer { respones ->
            Log.e("LINHHHHHHH", "$respones")
            if (respones.jwt == null) {

            }
        })
    }

}