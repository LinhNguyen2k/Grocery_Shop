package com.example.grocery_shop.view.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirm
import com.example.grocery_shop.databinding.ActivityLoginBinding
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity
import kotlinx.coroutines.delay

class LoginActivity : BaseVMActivity<ActivityLoginBinding, AuthenticationViewModel>() {

    private val viewModels by viewModels<AuthenticationViewModel>()

    private val confirmDialog by lazy {
        DialogConfirm(this)
    }

    override fun handleLoading(isCancel: Boolean?) {
        super.handleLoading(true)
    }

    override fun initView() {

    }

    fun forGotPassWord(username: String) {
        lifecycleScope.launchWhenCreated {
            viewModels.forGotPassWord(username, onComplete = { data ->
                if (data.status) {
                    confirmDialog.showDialogConfirm("Thay đổi mật khẩu thành công")
                } else {
                    confirmDialog.showDialogConfirm("Thông tin người dùng không chính xác")
                }

            })
        }
    }

    override fun initListener() {
        binding.tvSignupLogin.singleClick {
            lifecycleScope.launchWhenCreated {
                delay(500)
                openActivity(SignUpActivity::class.java, true)
            }
        }

        binding.btnLogin.singleClick {
            loginWithAccount()
        }

        binding.btnForgotPw.singleClick {
            when {
                binding.etLoginMobileNumber.text.isEmpty() -> {
                    showToast("Tài khoản không được để trống")
                }
                else -> {
                    forGotPassWord(binding.etLoginMobileNumber.text.toString())
                }
            }
        }
    }

    override fun initData() {

    }

    fun loginWithAccount() {
        when {
            binding.etLoginMobileNumber.text.isEmpty() -> {
                showToast("Tài khoản không được để trống")
            }
            binding.etLoginPassword.text!!.isEmpty() -> {
                showToast("Mật khẩu không được để trống")
            }
            else -> {
                lifecycleScope.launchWhenCreated {

                    viewModels.login(
                        binding.etLoginPassword.text.toString(),
                        binding.etLoginMobileNumber.text.toString(), onComplete = { data ->
                            if (data.jwt!!.isNotEmpty()) {
                                lifecycleScope.launchWhenCreated {
                                    openActivity(HomeActivity::class.java, true)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

}