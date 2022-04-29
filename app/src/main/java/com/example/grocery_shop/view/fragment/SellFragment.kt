package com.example.grocery_shop.view.fragment

import androidx.fragment.app.viewModels
import com.example.grocery_shop.R
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.adapter.ProductCartAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirm
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.FragmentSellBinding
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class SellFragment : BaseFragment<FragmentSellBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val productCartAdapter by lazy {
        ProductCartAdapter()
    }
    private val confirmDialog by lazy {
        DialogConfirmV2(requireContext())
    }


    override fun initView() {
        getAllCartUser(UserManager.getUserId(requireContext()).toString())
    }

    override fun initListener() {
        initListenerCart()
    }

    override fun initData() {
        observeAddCart()
    }

    private fun initListenerCart() {
        productCartAdapter.onTrashClickListenerAddCart = { data ->
            addCart(
                data.productId.toString(),
                1,
                UserManager.getUserId(requireContext()).toString()
            )
        }
        productCartAdapter.onTrashClickListenerSubtraction = {

        }
    }

    private fun getAllCartUser(userId: String) {
        viewModels.getAllProductCart(userId, onComplete = { response ->
            productCartAdapter.addData(response)
            RecyclerUtils.setGridManager(
                requireContext(),
                binding.RCListSpCar,
                productCartAdapter
            )
        })
    }

    private fun addCart(productId: String? = null, quantity: Int? = null, userId: String? = null) {
        var result = CartBody(productId, quantity, userId)
        viewModels.addProductIntoCart(result)
    }

    private fun observeAddCart() {

        viewModels.resultAddCart.observe(this, androidx.lifecycle.Observer { data ->
            if (data.id.userId != null) {
                confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_success))
            } else {
                confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_failure))
            }
        })
    }
}