package com.example.grocery_shop.view.activity.client

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivityLoginBinding
import com.example.grocery_shop.util.UserManager.getPassWord
import com.example.grocery_shop.util.UserManager.getUserName
import com.example.grocery_shop.util.UserManager.setFulLName
import com.example.grocery_shop.util.UserManager.setPassWord
import com.example.grocery_shop.util.UserManager.setToken
import com.example.grocery_shop.util.UserManager.setUserId
import com.example.grocery_shop.util.UserManager.setUserName
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.view.activity.admin.AdminHomeActivity
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity
import kotlinx.coroutines.delay
import android.content.Intent
import android.net.Uri


class LoginActivity : BaseVMActivity<ActivityLoginBinding, AuthenticationViewModel>() {
    companion object {
        const val CONTACT_REQUEST = 1
    }
    private val viewModels by viewModels<AuthenticationViewModel>()

    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }

//    override fun handleLoading(isCancel: Boolean?) {
//        super.handleLoading(true)
//    }

    override fun initView() {
        setUpLogin()
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
            openActivity(ForGotPasswordActivity::class.java)
        }

//        binding.btnLoginFb.singleClick {
//            val uri =
//                ("https://sale-server-app.herokuapp.com/oauth2/authorization/github")
//            var intent = Intent(this, WebViewActivity::class.java)
//            intent.putExtra("url", uri)
//            startActivity(intent)
//
////            i.data = Uri.parse(url)
////                viewModels.saveUserWhenLoginGithub(onComplete = { data ->
////                    finish()
////                    openActivity(LoginActivity::class.java)
////            })
//        }
    }

    override fun initData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONTACT_REQUEST) {

        }
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
                            if (data.roleName == "ROLE_ADMIN") {
                                lifecycleScope.launchWhenCreated {
                                    openActivity(AdminHomeActivity::class.java, true)
                                    setUserId(applicationContext, data.userId.toInt())
                                    setToken(applicationContext, data.jwt.toString())
                                    setUserName(applicationContext, data.username.toString())
                                    setFulLName(applicationContext, data.fullName.toString())
                                    setPassWord(
                                        applicationContext,
                                        binding.etLoginPassword.text.toString()
                                    )
                                }
                            } else {
                                lifecycleScope.launchWhenCreated {
                                    openActivity(HomeActivity::class.java, true)
                                    setUserId(applicationContext, data.userId.toInt())
                                    setToken(applicationContext, data.jwt.toString())
                                    setUserName(applicationContext, data.username.toString())
                                    setFulLName(applicationContext, data.fullName.toString())
                                    setPassWord(
                                        applicationContext,
                                        binding.etLoginPassword.text.toString()
                                    )
                                }
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