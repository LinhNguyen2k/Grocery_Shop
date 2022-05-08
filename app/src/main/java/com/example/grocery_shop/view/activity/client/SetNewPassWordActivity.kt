package com.example.grocery_shop.view.activity.client

import androidx.activity.viewModels
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivitySetNewPassWordBinding
import com.example.grocery_shop.model.auth.setNewPassBody
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class SetNewPassWordActivity : BaseVMActivity<ActivitySetNewPassWordBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()

    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }
    override fun initView() {
        confirmDialog.showDialogConfirm("Mã thay đổi mật khẩu đã được gửi về mail")

    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = {onBackPressed()}
        binding.layoutSubmitForgot.setOnClickListener {
            setNewPassWord(binding.etRegisterUsername.text.toString(), binding.etRegisterToken.text.toString(),
            binding.etRegisterNewPassWord.text.toString())
        }
    }

    override fun initData() {
    }

    private fun setNewPassWord(userName : String, token : String, newPassWord : String) {
        loadingDialog.show(this, "")
        val result = setNewPassBody(newPassWord, token, userName)
        viewModels.setNewPassWord(result, onComplete = {
            loadingDialog.dismiss()
            confirmDialog.showDialogConfirm("Thay đổi mật khẩu thành công")
        }, onErrors = {
            loadingDialog.dismiss()
            confirmDialog.showDialogConfirm("Thay đổi mật khẩu thất bại công")
        })
    }

}