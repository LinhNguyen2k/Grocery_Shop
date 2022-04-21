package com.example.grocery_shop.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirm
import com.example.grocery_shop.databinding.ActivityLoginBinding
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity
import kotlinx.coroutines.delay

class LoginActivity : BaseVMActivity<ActivityLoginBinding, AuthenticationViewModel>() {
    private val authenticationViewModel by lazy {
        ViewModelProvider(this)[AuthenticationViewModel::class.java]
    }
    private val confirmDialog by lazy {
        DialogConfirm(this)
    }


    override fun initView() {

    }

    override fun initListener() {
        binding.tvSignupLogin.singleClick {
            lifecycleScope.launchWhenCreated {
                delay(100)
                openActivity(SignUpActivity::class.java, true)
            }
        }

        binding.btnLogin.singleClick {
            when {
                binding.etLoginMobileNumber.text.isEmpty() -> {
                    showToast("Tài khoản không được để trống")
                }
                binding.etLoginPassword.text!!.isEmpty() -> {
                    showToast("Mật khẩu không được để trống")
                }
                else -> {
                    lifecycleScope.launchWhenCreated {
                        authenticationViewModel.login(
                            binding.etLoginPassword.text.toString(),
                            binding.etLoginMobileNumber.text.toString()
                        )
                    }
                }
            }

        }
    }

    override fun initData() {
        authenticationViewModel.resultLogin.observe(this, Observer { respones ->
            if (respones.jwt != null) {
                    lifecycleScope.launchWhenCreated {
                        openActivity(HomeActivity::class.java, true)
                    }
            } else {
                confirmDialog.showDialogConfirm(respones.message.toString())

            }
        })

    }

}