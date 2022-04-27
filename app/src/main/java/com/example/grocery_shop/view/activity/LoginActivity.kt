package com.example.grocery_shop.view.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirm
import com.example.grocery_shop.databinding.ActivityLoginBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.util.UserManager.getPassWord
import com.example.grocery_shop.util.UserManager.getUserName
import com.example.grocery_shop.util.UserManager.setFulLName
import com.example.grocery_shop.util.UserManager.setPassWord
import com.example.grocery_shop.util.UserManager.setToken
import com.example.grocery_shop.util.UserManager.setUserId
import com.example.grocery_shop.util.UserManager.setUserName
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
        setUpLogin()
    }

    private fun forGotPassWord(username: String) {
        lifecycleScope.launchWhenCreated {
            viewModels.forGotPassWord(username, onComplete = { data ->
                confirmDialog.showDialogConfirm("Thay đổi mật khẩu thành công")
            }, onErrors = { err ->
                confirmDialog.showDialogConfirm("Thay đổi mật khẩu không thành công")
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
                else ->
                    forGotPassWord(binding.etLoginMobileNumber.text.toString())
            }
        }
    }

    override fun initData() {

    }

    private fun loginWithAccount() {
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
                            lifecycleScope.launchWhenCreated {
                                openActivity(HomeActivity::class.java, true)
                                setUserId(applicationContext, data.userId.toInt())
                                setToken(applicationContext, data.jwt.toString())
                                setUserName(applicationContext, data.username.toString())
                                setFulLName(applicationContext, data.username.toString())
                                setPassWord(
                                    applicationContext,
                                    binding.etLoginPassword.text.toString()
                                )
                            }
                        }, onErrors = {
                            confirmDialog.showDialogConfirm(getString(R.string.message_error_username_or_password))
                        }
                    )
                }
            }
        }
    }

    private fun setUpLogin() {
        if (binding.cbLoginRemember.isChecked && getUserName(applicationContext).isNotEmpty() && getPassWord(applicationContext).isNotEmpty()) {
            binding.etLoginPassword.setText(getPassWord(applicationContext))
            binding.etLoginMobileNumber.setText(getUserName(applicationContext))
        } else {
            binding.etLoginPassword.setText("")
            binding.etLoginMobileNumber.setText("")
        }
    }

}