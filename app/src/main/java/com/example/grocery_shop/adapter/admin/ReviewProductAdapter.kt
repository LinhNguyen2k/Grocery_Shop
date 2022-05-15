package com.example.grocery_shop.adapter.admin

import android.annotation.SuppressLint
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemViewAllOrderBinding
import com.example.grocery_shop.databinding.LayoutFeedbackDetailItemBinding
import com.example.grocery_shop.model.review.reviewResponse
import com.example.grocery_shop.model.review.reviewResponseItem
import com.example.grocery_shop.response.order.orderResponseManage
import java.lang.Exception
import java.text.SimpleDateFormat

class ReviewProductAdapter : BaseRecyclerViewAdapter<reviewResponseItem, LayoutFeedbackDetailItemBinding>() {

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun bindData(
        binding: LayoutFeedbackDetailItemBinding,
        item: reviewResponseItem,
        position: Int
    ) {
        binding.tvName.text = "Nguyễn Anh Linh"
        try {
            binding.tvContent.text = item.comments

        } catch (e : Exception) {

        }

    }


}
