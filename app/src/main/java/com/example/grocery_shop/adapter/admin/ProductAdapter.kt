package com.example.grocery_shop.adapter.admin

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemProductManageBinding
import com.example.grocery_shop.model.category.productList

class ProductAdapter: BaseRecyclerViewAdapter<productList, CustomItemProductManageBinding>() {
    var onTrashClickListenerDelete: ((item: productList) -> Unit)? = null
    var onTrashClickListenerEdit: ((item: productList) -> Unit)? = null
    @SuppressLint("SetTextI18n")
    override fun bindData(
        binding: CustomItemProductManageBinding,
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



        binding.btnEdit.setOnClickListener {
            onTrashClickListenerEdit?.invoke(item)

        }

        binding.btnAddDelete.setOnClickListener {
            onTrashClickListenerDelete?.invoke(item)

        }


    }


}
