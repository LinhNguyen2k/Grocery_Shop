package com.example.grocery_shop.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirm
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.FragmentDetailProductBinding
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.view.activity.HomeActivity
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import java.text.NumberFormat
import java.util.*

class DetailProductFragment :
    BaseFragment<FragmentDetailProductBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val categoryListOne by lazy {
        CategoryAdapter()
    }
    private val confirmDialog by lazy {
        DialogConfirmV2(requireContext())
    }
    var item: productList? = null
    override fun initView() {
        val bundle = arguments
        getInfoProduct(bundle)
        getListProduct(bundle?.getString("category_key").toString())

    }

    override fun onResume() {
        isLoading.value = false
        super.onResume()
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        setClickAddCart()
        setClickItem()
    }

    override fun initData() {
    }

    private fun getInfoProduct(bundle: Bundle?) {
        item = bundle?.getParcelable(Constants.KEY_PRODUCT_SELECTED)
        val iv: String = item?.productImage.toString()
        binding.tvItemTitle.text = item?.productName
        if (TextUtils.isEmpty(iv)) {
        } else {
            try {
                Glide.with(requireContext())
                    .load(iv)
                    .error(R.drawable.iv_no_image)
                    .into(binding.imgProduct)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }

        binding.tvItemOldPrice.text = item?.oldUnitPrice.toString()

        val local = Locale("vi", "VN")
        val numberFormat = NumberFormat.getInstance(local)
        val money: String = numberFormat.format(item?.unitPrice)
        binding.tvItemNewPrice.text = money
        binding.tvItemContent.text = item?.descriptionProduct
        binding.tvQuantity.text = item?.quantity.toString()
    }

    private fun getListProduct(id: String) {
        viewModels.getCategoryDetail(null, id, onComplete = { data ->
            categoryListOne.addMoreData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.RCmoreSPChay,
                categoryListOne
            )
        })
    }

    private fun setClickAddCart() {
        binding.addCart.setOnClickListener {
            addCart(
                item?.productId.toString(), 1,
                UserManager.getUserId(requireContext()).toString()
            )
        }
    }

    private fun setClickItem() {
        categoryListOne.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", bundle.getString("category_key").toString())
            arguments = bundle
            getInfoProduct(bundle)
        }

    }

    private fun addCart(productId: String? = null, quantity: Int? = null, userId: String? = null) {
        var result = CartBody(productId, quantity, userId)
        viewModels.addProductIntoCart(result, onComplete = { data ->
            confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_success))

        }, onErrors = { errorResponse ->
            confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_failure))
        })
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun observeAddCart(){
//        viewModels.resultAddCart.observe(this, androidx.lifecycle.Observer { data ->
//            if (data.id.userId != null) {
//                categoryListOne.notifyDataSetChanged()
//                confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_success))
//            } else {
//                confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_failure))
//            }
//        })
//    }

}