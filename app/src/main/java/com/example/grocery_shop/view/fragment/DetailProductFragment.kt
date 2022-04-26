package com.example.grocery_shop.view.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.databinding.FragmentDetailProductBinding
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import java.text.NumberFormat
import java.util.*

class DetailProductFragment :
    BaseFragment<FragmentDetailProductBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    var item : productList? = null
    override fun initView() {
        val bundle = arguments
        getInfoProduct(bundle)

    }

    override fun initListener() {
    }

    override fun initData() {
    }

    private fun getInfoProduct(bundle: Bundle?){
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

//    private fun getInfoProduct(id: String) {
//        viewModels.getInfoProduct(id, onComplete = { response ->
//                binding.tvItemStatus.text = response.descriptionProduct
//        })
//    }

}