package com.example.grocery_shop.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemCartBinding
import com.example.grocery_shop.model.category.productList
import java.text.NumberFormat
import java.util.*

class ProductCartAdapter : BaseRecyclerViewAdapter<productList, CustomItemCartBinding>() {
    var onTrashClickListenerDelete: ((item: productList) -> Unit)? = null
    var onTrashClickListenerAddCart: ((item: productList) -> Unit)? = null
    var onTrashClickListenerSubtraction: ((item: productList) -> Unit)? = null
    var onTrashClickListener: ((item: productList) -> Unit)? = null
    @SuppressLint("SetTextI18n")
    override fun bindData(
        binding: CustomItemCartBinding,
        item: productList,
        position: Int
    ) {

        var count : Int? = item.quantity

        val iv: String = item.productImage.toString()
        binding.tvNameProduct.text = item.productName
            try {
                Glide.with(context)
                    .load(iv)
                    .error(R.drawable.page1)
                    .into(binding.imgItem)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        binding.tvResult.text = (item.quantity).toString()

        val local = Locale("vi", "VN")
        val numberFormat = NumberFormat.getInstance(local)
        val money: String = numberFormat.format(item.unitPrice)
        binding.tvPriceProduct.text = money
        val resultMoney : String = numberFormat.format(item.unitPrice?.times((item.quantity!!)))

        binding.tvResultMoney.text = resultMoney

        binding.tvSubtraction.setOnClickListener {
            onTrashClickListenerSubtraction?.invoke(item)
            count = count!! - 1
            binding.tvResult.text = (count).toString()
            val resultMoney : String = numberFormat.format(item.unitPrice!! * count!!)
            binding.tvResultMoney.text = resultMoney
        }

        binding.tvAddCart.setOnClickListener {
            onTrashClickListenerAddCart?.invoke(item)
            count = count!! + 1
            binding.tvResult.text = (count).toString()
            val resultMoney : String = numberFormat.format(item.unitPrice!! * count!!)
            binding.tvResultMoney.text = resultMoney
        }

        binding.ivDeleteProduct.setOnClickListener {
            onTrashClickListenerDelete?.invoke(item)

        }
         binding.imgItem.setOnClickListener {
             onTrashClickListener?.invoke(item)
        }

    }


}


