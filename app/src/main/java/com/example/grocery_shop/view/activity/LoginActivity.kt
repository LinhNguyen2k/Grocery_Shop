package com.example.grocery_shop.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.databinding.ActivityLoginBinding
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity
import kotlinx.coroutines.delay

class LoginActivity : BaseVMActivity<ActivityLoginBinding, AuthenticationViewModel>() {

    override fun initView() {

    }

    override fun initListener() {
        binding.tvSignupLogin.singleClick {
            lifecycleScope.launchWhenCreated {
                Toast.makeText(applicationContext, "hi", Toast.LENGTH_LONG).show()
                delay(500)
                openActivity(SignUpActivity::class.java, true)
            }
        }
    }

    override fun initData() {

    }

}