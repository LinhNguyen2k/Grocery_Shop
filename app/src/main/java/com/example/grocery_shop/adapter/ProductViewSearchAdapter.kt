package com.example.grocery_shop.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemCartBinding
import com.example.grocery_shop.databinding.CustomItemViewSearchBinding
import com.example.grocery_shop.model.category.productList
import java.text.NumberFormat
import java.util.*

class ProductViewSearchAdapter : BaseRecyclerViewAdapter<productList, CustomItemViewSearchBinding>() {
    var onTrashClickListener: ((item: productList) -> Unit)? = null
    @SuppressLint("SetTextI18n")
    override fun bindData(
        binding: CustomItemViewSearchBinding,
        item: productList,
        position: Int
    ) {


        val iv: String = item.productImage.toString()
        binding.tvNameProduct.text = item.productName
            try {
                Glide.with(context)
                    .load(iv)
                    .error(R.drawable.iv_no_image)
                    .into(binding.imgItem)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }

        val local = Locale("vi", "VN")
        val numberFormat = NumberFormat.getInstance(local)
        val money: String = numberFormat.format(item.unitPrice)
        binding.tvPriceProduct.text = money
        binding.tvAbuout.text = item.descriptionProduct

         binding.imgItem.setOnClickListener {
             onTrashClickListener?.invoke(item)
        }

    }


}


