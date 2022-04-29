package com.example.grocery_shop.view.fragment

import androidx.fragment.app.viewModels
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.adapter.ProductCartAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirm
import com.example.grocery_shop.databinding.FragmentSellBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class SellFragment : BaseFragment<FragmentSellBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val productCartAdapter by lazy {
        ProductCartAdapter()
    }
    private val confirmDialog by lazy {
        DialogConfirm(requireContext())
    }

    fun getAllCartUser(userId : String){
        viewModels.getAllProductCart(userId, onComplete = { response ->
            productCartAdapter.addData(response)
            RecyclerUtils.setGridManager(
                requireContext(),
                binding.RCListSpCar,
                productCartAdapter
            )
        })
    }

    override fun initView() {
        getAllCartUser(UserManager.getUserId(requireContext()).toString())
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}