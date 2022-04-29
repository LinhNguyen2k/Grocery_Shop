package com.example.grocery_shop.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemCartBinding
import com.example.grocery_shop.databinding.CustomItemProductBinding
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.response.CartGetAllResponseItem
import java.text.NumberFormat
import java.util.*

class ProductCartAdapter : BaseRecyclerViewAdapter<CartGetAllResponseItem, CustomItemCartBinding>() {
    var onTrashClickListener: ((item: CartGetAllResponseItem) -> Unit)? = null

    override fun bindData(
        binding: CustomItemCartBinding,
        item: CartGetAllResponseItem,
        position: Int
    ) {
        val iv: String = item.productImage
        binding.tvNameProduct.text = item.productName
        if (TextUtils.isEmpty(iv)) {
        } else {
            try {
                Glide.with(context)
                    .load(iv)
                    .error(R.drawable.iv_no_image)
                    .into(binding.imgItem)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
        binding.tvResult.text = item.quantity.toString()

        val local = Locale("vi", "VN")
        val numberFormat = NumberFormat.getInstance(local)
        val money: String = numberFormat.format(item.unitPrice)
        binding.tvPriceProduct.text = money

//        binding.customListSPBanChay.setOnClickListener {
//            onTrashClickListener?.invoke(item)
//        }
    }
}