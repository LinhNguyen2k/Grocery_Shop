package com.example.grocery_shop.view.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.PriceHelper
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.FragmentOrderBinding
import com.example.grocery_shop.model.order.orderBody
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderFragment : BaseFragment<FragmentOrderBinding, AuthenticationViewModel>() {

    lateinit var responseOrder : orderResponse

    override fun initView() {
        val bundle = arguments
        responseOrder = bundle?.getParcelable("info_order")!!
        binding.tvAddress.text = responseOrder.address
        binding.tvNote.text = responseOrder.note
        binding.tvPhoneNumber.text = responseOrder.phoneNumber
        val price = PriceHelper.getPriceFormat(responseOrder.totalAmount)
        binding.tvResultMoney.text = price
        @SuppressLint("SimpleDateFormat")
        val simpleDateFormat = SimpleDateFormat("EEE MMM dd hh:mm aaa", Locale.ENGLISH)
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//        binding.tvPublishedAt.text = simpleDateFormat.format(formatter.parse(responseOrder.createDate))
    }

    override fun initListener() {

    }

    override fun initData() {

    }



}