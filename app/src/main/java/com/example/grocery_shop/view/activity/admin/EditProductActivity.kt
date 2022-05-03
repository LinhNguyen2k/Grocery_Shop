package com.example.grocery_shop.view.activity.admin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivityEditProductBinding
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.product.bodyEditProduct
import com.example.grocery_shop.model.product.productAddBody
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.mobile.mbccs.base.component.navigator.openActivity

private const val PERMISSION_CODE = 1001
private const val IMAGE_PICK_CODE = 1000

class EditProductActivity : BaseVMActivity<ActivityEditProductBinding, AuthenticationViewModel>() {
    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }


    private val viewModels by viewModels<AuthenticationViewModel>()
    override fun initView() {

        initViewProduct()

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun initListener() {
        binding.btnConfirm.setOnClickListener {
            editProduct(
                binding.edNameProduct.text.toString(),
                binding.edMoney.text.toString().toInt(),
                binding.edOldPrice.text.toString().toInt(),
                binding.edDiscount.text.toString().toInt(),
                binding.edQuality.text.toString().toInt(),
                binding.edAbout.text.toString()
            )
        }
        binding.imgChooseImg.setOnClickListener {
            checkPerPermission()
        }
        binding.toolbar.onLeftClickListener = {
            finish()
            openActivity(ManageProductActivity::class.java)
        }
    }

    override fun initData() {
    }

    private fun editProduct(
        nameProduct: String,
        price: Int,
        oldPrice: Int,
        discount: Int,
        quality: Int,
        description: String
    ) {
        val bundle = intent.getParcelableExtra<productList>("EditProductManage") as productList
        val body =
            bodyEditProduct(description, discount, oldPrice, null, nameProduct, quality, price)
        loadingDialog.show(this, "")
        viewModels.editInfoProduct(
            "Bearer " + UserManager.getToken(this),
            bundle.productId.toString(),
            body,
            onComplete = { data ->
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Sửa sản phẩm thành công")
            },
            onErrors = { err ->
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Sửa sản phẩm không thành công")

            })
    }


    private fun initViewProduct() {
        val bundle = intent.getParcelableExtra<productList>("EditProductManage") as productList
        try {
            Glide.with(this)
                .load(bundle.productImage)
                .error(R.drawable.iv_no_image)
                .into(binding.imgAddProduct)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        binding.edNameProduct.setText(bundle.productName)
        binding.edMoney.setText(bundle.unitPrice.toString())
        binding.edOldPrice.setText(bundle.oldUnitPrice.toString())
        binding.edQuality.setText(bundle.quantity.toString())
        binding.edAbout.setText(bundle.descriptionProduct)
        binding.edDiscount.setText(bundle.discount.toString())
    }


    private fun chooseImageLocal() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent, IMAGE_PICK_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPerPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(
                permissions, PERMISSION_CODE
            );
        } else {
            chooseImageLocal()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    chooseImageLocal()
                } else {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val bundle = intent.getParcelableExtra<productList>("EditProductManage") as productList
            data?.let {
                loadingDialog.show(this, "")
                viewModel.compressorImageProduct(
                    "Bearer " + UserManager.getToken(this),
                    bundle.productId.toString(),
                    it,
                    onComplete = { bit ->
                        binding.imgAddProduct.setImageBitmap(bit)
                        loadingDialog.dismiss()
                    })
            }
        }
    }
}