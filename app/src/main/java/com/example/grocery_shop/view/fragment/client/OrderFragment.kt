package com.example.grocery_shop.view.fragment.client

import android.annotation.SuppressLint
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.PriceHelper
import com.example.grocery_shop.databinding.FragmentOrderBinding
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderFragment : BaseFragment<FragmentOrderBinding, AuthenticationViewModel>() {

    lateinit var responseOrder : orderResponse

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val bundle = arguments
        responseOrder = bundle?.getParcelable("info_order")!!
        binding.tvAddress.text = "Địa chỉ: " + responseOrder.address
        binding.tvNote.text = responseOrder.note
        binding.tvPhoneNumber.text ="Số điện thoại: "  + responseOrder.phoneNumber
        val price = PriceHelper.getPriceFormat(responseOrder.totalAmount)
        binding.tvResultMoney.text = "Tổng tiền: $price"
        @SuppressLint("SimpleDateFormat")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val format = responseOrder.createDate.substring(0, 9)
        binding.tvPublishedAt.text ="Thời gian: " + formatter.parse(format).toString()
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = {onBackPressed()}

    }

    override fun initData() {

    }



}