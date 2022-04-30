package com.example.grocery_shop.adapter

import android.graphics.Paint
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemProductBinding
import com.example.grocery_shop.model.category.productList
import java.text.NumberFormat
import java.util.*

class CategoryAdapter : BaseRecyclerViewAdapter<productList, CustomItemProductBinding>() {
    var onTrashClickListener: ((item: productList) -> Unit)? = null
    override fun bindData(binding: CustomItemProductBinding, item: productList, position: Int) {
        val iv: String = item.productImage.toString()
        binding.tvNameSpBanChay.text = item.productName
        if (TextUtils.isEmpty(iv)) {
        } else {
            try {
                Glide.with(context)
                    .load(iv)
                    .error(R.drawable.iv_no_image)
                    .into(binding.imageSpBanChay)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }

        binding.tvOldPriceSpBanChay.text = item.oldUnitPrice.toString()
        binding.tvOldPriceSpBanChay.paintFlags =  binding.tvOldPriceSpBanChay.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        val local = Locale("vi", "VN")
        val numberFormat = NumberFormat.getInstance(local)
        val money: String = numberFormat.format(item.unitPrice)
        binding.tvPriceSpBanChay.text = money

        binding.customListSPBanChay.setOnClickListener {
            onTrashClickListener?.invoke(item)
        }

    }
}