package com.example.grocery_shop.view.activity.admin

import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.databinding.ActivityAdminHomeBinding
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity

class AdminHomeActivity : BaseVMActivity<ActivityAdminHomeBinding, AuthenticationViewModel>() {


    override fun initView() {
    }

    override fun initListener() {
        binding.layoutManageProduct.setOnClickListener {
            openActivity(ManageProductActivity::class.java)
        }
        binding.layoutManageOrder.setOnClickListener {
            openActivity(ViewAllOrderActivity::class.java)
        }
        binding.layoutManageAccount.setOnClickListener {
            openActivity(AccountActivity::class.java)
        }
        binding.layoutChartOrder.setOnClickListener {
            openActivity(ChartOrderActivity::class.java)
        }
    }

    override fun initData() {
    }
}