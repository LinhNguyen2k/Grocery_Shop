package com.example.grocery_shop.adapter.admin

import android.annotation.SuppressLint
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemViewAllOrderBinding
import com.example.grocery_shop.databinding.LayoutFeedbackDetailItemBinding
import com.example.grocery_shop.model.review.bodyReview_v2Item
import com.example.grocery_shop.model.review.reviewResponse
import com.example.grocery_shop.model.review.reviewResponseItem
import com.example.grocery_shop.response.order.orderResponseManage
import java.lang.Exception
import java.text.SimpleDateFormat

class ReviewProductAdapter : BaseRecyclerViewAdapter<bodyReview_v2Item, LayoutFeedbackDetailItemBinding>() {

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun bindData(
        binding: LayoutFeedbackDetailItemBinding,
        item: bodyReview_v2Item,
        position: Int
    ) {
        binding.tvName.text = item.name
        try {
            binding.tvContent.text = item.comments

        } catch (e : Exception) {

        }

    }


}
