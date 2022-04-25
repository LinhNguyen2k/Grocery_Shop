package com.example.grocery_shop.view.fragment

import android.text.TextUtils
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.databinding.FragmentDetailProductBinding
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class DetailProductFragment : BaseFragment<FragmentDetailProductBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    override fun initView() {
        val bundle = arguments
        val idProduct = bundle!!.getString(Constants.KEY_PRODUCT_SELECTED)


    }

    override fun initListener() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}