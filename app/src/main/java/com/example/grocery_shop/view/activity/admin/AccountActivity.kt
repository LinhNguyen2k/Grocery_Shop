package com.example.grocery_shop.view.activity.admin

import androidx.activity.viewModels
import com.example.grocery_shop.adapter.admin.OrderAdapter
import com.example.grocery_shop.adapter.admin.UserAdapter
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.databinding.ActivityAccountBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class AccountActivity : BaseVMActivity<ActivityAccountBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val categoryListOne by lazy {
        UserAdapter()
    }

    override fun initView() {
        getAllAccount()
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
    }

    override fun initData() {
    }

    private fun getAllAccount() {
        loadingDialog.show(this, "")
        viewModels.getAllAccount("Bearer " + UserManager.getToken(this), onComplete = { data ->
            loadingDialog.dismiss()
            categoryListOne.addData(data)
            RecyclerUtils.setGridManager(
                applicationContext,
                binding.rcAccount,
                categoryListOne
            )
        }, onErrors = {
            loadingDialog.dismiss()

        })
    }
}