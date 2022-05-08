package com.example.grocery_shop.view.fragment.client

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.PriceHelper
import com.example.grocery_shop.databinding.FragmentOrderBinding
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class OrderFragment : BaseFragment<FragmentOrderBinding, AuthenticationViewModel>() {

    lateinit var responseOrder : orderResponse

    @RequiresApi(Build.VERSION_CODES.O)
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
        binding.tvPublishedAt.text = "Thời gian: " + responseOrder.createDate
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = {onBackPressed()}

    }

    override fun initData() {

    }



}