package com.example.grocery_shop.view.activity.client

import androidx.activity.viewModels
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivityForGotPasswordBinding
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity

class ForGotPasswordActivity : BaseVMActivity<ActivityForGotPasswordBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()

    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = {onBackPressed()}
        subMitForGotPass()
    }

    override fun initData() {
    }

    private fun subMitForGotPass() {
        binding.layoutSubmitForgot.setOnClickListener {
            loadingDialog.show(this, "")
            viewModels.forGotPassWord(binding.etRegisterUsername.text.toString(), onComplete = {
                loadingDialog.dismiss()
                openActivity(SetNewPassWordActivity::class.java)
            }, onErrors = {
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Không tìm thấy tài khoản ${binding.etRegisterUsername.text}")
            })
        }
    }

}