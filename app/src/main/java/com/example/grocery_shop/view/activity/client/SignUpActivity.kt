package com.example.grocery_shop.view.activity.client

import androidx.activity.viewModels
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivitySignUpBinding
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity

class SignUpActivity : BaseVMActivity<ActivitySignUpBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    override fun initView() {
    }
    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }
    override fun initListener() {

        binding.btnSignUp.singleClick {
            signUp()
        }
        binding.tvLogin.setOnClickListener {
            finish()
            openActivity(LoginActivity::class.java)
        }
        binding.toolbar.onLeftClickListener = {onBackPressed()}

    }

    private fun signUp() {
        loadingDialog.show(this, "")
        viewModels.signUp(
            null,
            binding.etEmail.text.toString(),
            binding.etFullName.text.toString(),
            binding.etLoginMobileNumber.text.toString(),
            binding.etUserName.text.toString(),onComplete = {
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Đăng ký thành công")
            }, onErrors = {  err ->
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Tên tài khoản hoặc email này đã được sủ dụng.")
            }
        )
    }

    override fun initData() {

    }

}