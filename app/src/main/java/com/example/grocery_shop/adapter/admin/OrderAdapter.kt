package com.example.grocery_shop.adapter.admin

import android.annotation.SuppressLint
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomItemViewAllOrderBinding
import com.example.grocery_shop.response.order.orderResponseManage
import java.text.SimpleDateFormat

class OrderAdapter : BaseRecyclerViewAdapter<orderResponseManage, CustomItemViewAllOrderBinding>() {
    var onTrashClickListenerDelete: ((item: orderResponseManage) -> Unit)? = null
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun bindData(
        binding: CustomItemViewAllOrderBinding,
        item: orderResponseManage,
        position: Int
    ) {

        binding.tvNameProduct.text =item.note
        binding.tvAddress.text ="Địa chỉ: " + item.address
//        if (item.fullName.toString().isEmpty()){
//            binding.tvNameOrder.text = "Nguyễn Anh Linh"
//        } else {
//            binding.tvNameOrder.text  = item.fullName.toString()
//        }

        binding.tvSumMoney.text ="Tổng tiền: " + item.totalAmount.toString()
        binding.tvPhoneNumber.text ="Số điện thoại: " + item.phoneNumber

//        binding.tvQuantity.text = item.orderDetailEntities[position].quantity.toString()
        val fomat = item.createDate.substring(0, 9)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        binding.tvDateOrder.text = formatter.parse(fomat).toString()
        binding.tvDateOrder.text ="Thời gian: " + item.createDate


        binding.btnAddDelete.setOnClickListener {
            onTrashClickListenerDelete?.invoke(item)

        }


    }


}
