package com.example.grocery_shop.view.activity.admin

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.adapter.admin.ProductAdapter
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivityManageProductBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity

class ManageProductActivity :
    BaseVMActivity<ActivityManageProductBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val categoryListOne by lazy {
        ProductAdapter()
    }
    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }

    override fun initView() {
        getAllProduct()
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { finish() }
        binding.btAddProduct.setOnClickListener {
            openActivity(UploadProductActivity::class.java)
        }
        deleteProduct()
    }

    override fun initData() {
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteProduct() {
        categoryListOne.onTrashClickListenerDelete = { data ->
            viewModels.deleteProduct(
                "Bearer " + UserManager.getToken(applicationContext),
                data.productId.toString(),
                onComplete = { data ->
                    confirmDialog.showDialogConfirm("Xóa thành công")

                },
                onErrors = {
                    confirmDialog.showDialogConfirm("Xóa không thành công")
                })
//            categoryListOne.dataList.remove(data)
//            categoryListOne.notifyDataSetChanged()
        }
    }

    private fun getAllProduct() {
        viewModels.getCategory(null, null, onComplete = { data ->
            categoryListOne.addData(data)
            RecyclerUtils.setGridManager(
                applicationContext,
                binding.rcManageProduct,
                categoryListOne
            )
        }, onErrors = {

        })
    }
}