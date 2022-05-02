package com.example.grocery_shop.view.activity.admin

import androidx.activity.viewModels
import com.example.grocery_shop.adapter.admin.OrderAdapter
import com.example.grocery_shop.adapter.admin.ProductAdapter
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivityViewAllOrderBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class ViewAllOrderActivity : BaseVMActivity<ActivityViewAllOrderBinding, AuthenticationViewModel>() {

    private val viewModels by viewModels<AuthenticationViewModel>()
    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }
    private val categoryListOne by lazy {
        OrderAdapter()
    }
    override fun initView() {
        getAllOrder()
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = {finish()}
        categoryListOne.onTrashClickListenerDelete = { data ->
            viewModels.deleteOrder(UserManager.getToken(this), data.orderId.toString(), onComplete = { response ->
                confirmDialog.showDialogConfirm("Xóa đơn hàng thành công")
                categoryListOne.dataList.remove(data)
                categoryListOne.notifyDataSetChanged()
            }, onErrors = {
                confirmDialog.showDialogConfirm("Xóa đơn hàng không thành công")
            })
        }
    }

    override fun initData() {
    }

    private fun getAllOrder() {
        loadingDialog.show(this, "")
        viewModels.getListOrder( onComplete = { data ->
            loadingDialog.dismiss()
            categoryListOne.addData(data)
            RecyclerUtils.setGridManager(
                applicationContext,
                binding.rcManageOrder,
                categoryListOne
            )
        }, onErrors = {
            loadingDialog.dismiss()

        })
    }
}