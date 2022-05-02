package com.example.grocery_shop.view.activity.admin

import android.Manifest
import android.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.ActivityUploadProductBinding
import com.example.grocery_shop.model.product.productAddBody
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

private const val PERMISSION_CODE = 1001
private const val IMAGE_PICK_CODE = 1000

class UploadProductActivity :
    BaseVMActivity<ActivityUploadProductBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    val option = arrayOf(
        "Sản phẩm khuyến mãi",
        "Rau - Củ - Trái Cây",
        "Thịt - Trứng - Hải Sản",
        "Thực Phẩm Đông Lạnh",
        "Bánh Kẹo - Đồ Ăn Vặt"
    )
    var methodPay: String = ""
    private val confirmDialog by lazy {
        DialogConfirmV2(this)
    }

    override fun initView() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initListener() {
        setUpSpinner()
        binding.imgChooseImg.setOnClickListener {
            checkPerPermission()
        }
        binding.toolbar.onLeftClickListener = {finish()}
        binding.btnConfirm.setOnClickListener {
            if (binding.edNameProduct.text.toString()
                    .isEmpty() || methodPay.isEmpty() || binding.edMoney.text.toString()
                    .isEmpty() ||
                binding.edAbout.text.toString().isEmpty()
            ) {
                confirmDialog.showDialogConfirm("Bạn cần nhập đầy đủ thông tin")
            } else {

                addProduct(
                    binding.edNameProduct.text.toString(),
                    binding.edMoney.text.toString().toInt(),
                    binding.edOldPrice.text.toString().toInt(),
                    binding.edDiscount.text.toString().toInt(),
                    binding.edQuality.text.toString().toInt(),
                    binding.edAbout.text.toString()
                )
            }

        }
    }

    override fun initData() {
    }

    private fun addProduct(
        nameProduct: String,
        price: Int,
        oldPrice: Int,
        discount: Int,
        quality: Int,
        description: String
    ) {
        val body =
            productAddBody(description, discount, oldPrice, "", nameProduct, quality, price)
        when (methodPay) {
            "Sản phẩm khuyến mãi" -> methodPay = "1"
            "Rau - Củ - Trái Cây" -> methodPay = "2"
            "Thịt - Trứng - Hải Sản" -> methodPay = "3"
            "Thực Phẩm Đông Lạnh" -> methodPay = "4"
            "Bánh Kẹo - Đồ Ăn Vặt" -> methodPay = "5"
        }
        loadingDialog.show(this,"")
        viewModels.addProduct(
            "Bearer " + UserManager.getToken(this),
            "1",
            body,
            onComplete = { data ->
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Thêm sản phẩm thành công")
            },
            onErrors = { err ->
                loadingDialog.dismiss()
                confirmDialog.showDialogConfirm("Thêm sản phẩm không thành công")

            })
    }

    private fun setUpSpinner() {
        binding.spCategory.adapter = ArrayAdapter<String>(
            applicationContext,
            R.layout.simple_expandable_list_item_1,
            option
        )
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                methodPay = option[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

        }
    }


    private fun chooseImageLocal() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPerPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(permissions, PERMISSION_CODE);
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
            data?.let { data ->
                binding.imgAddProduct.setImageURI(data.data)
//                binding.btnConfirm.setOnClickListener {
//                    viewModel.compressorImage(UserManager.getUserId(applicationContext).toString(), data, onComplete = { bit ->
//                    })
//                }

            }
        }
    }

}