package com.example.grocery_shop.view.activity.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.adapter.CustomAnimation
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
        binding.rcManageProduct.itemAnimator = CustomAnimation()
        binding.btAddProduct.setOnClickListener {

            openActivity(UploadProductActivity::class.java)
        }
        editProduct()
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
                onComplete = { data2 ->
                    confirmDialog.showDialogConfirm("Xóa thành công")
                    categoryListOne.dataList.remove(data)
                    categoryListOne.notifyDataSetChanged()
                },
                onErrors = {
                    confirmDialog.showDialogConfirm("Xóa không thành công")
                })

        }
    }

    private fun editProduct() {
        categoryListOne.onTrashClickListenerEdit = { data ->
            finish()
            var intent = Intent(this, EditProductActivity::class.java)
            intent.putExtra("EditProductManage",data)
            startActivity(intent)

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