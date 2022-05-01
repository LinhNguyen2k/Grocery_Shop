package com.example.grocery_shop.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.grocery_shop.R
import com.example.grocery_shop.adapter.CustomAnimation
import com.example.grocery_shop.adapter.ProductCartAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.PriceHelper
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.FragmentSellBinding
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.deleteCartBody
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.response.CartGetAllResponseItem
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.view.activity.HomeActivity
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
        isLoading.value = false
        getAllCartUser(UserManager.getUserId(requireContext()).toString())
        binding.RCListSpCar.itemAnimator = CustomAnimation()

    }

    override fun initListener() {
        initListenerCart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initData() {
//        observeAddCart()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListenerCart() {
        productCartAdapter.onTrashClickListenerAddCart = { data ->
            addCart(
                data.productId.toString(), 1, UserManager.getUserId(requireContext()).toString()
            )
            getAllListAfterChange()

        }
        productCartAdapter.onTrashClickListenerSubtraction = { data ->
            addCart(
                data.productId.toString(), -1, UserManager.getUserId(requireContext()).toString()
            )
            getAllListAfterChange()
        }
        productCartAdapter.onTrashClickListenerDelete = { data ->
            deleteCart(data.productId.toString(), UserManager.getUserId(requireContext()).toString())
            productCartAdapter.dataList.remove(data)
            productCartAdapter.notifyDataSetChanged()
            resultMoney(productCartAdapter.dataList)
        }
        productCartAdapter.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", "1")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getAllListAfterChange(){
        viewModels.getAllProductCart(UserManager.getUserId(requireContext()).toString(), onComplete = { response ->
            productCartAdapter.dataList = response as MutableList<productList>
            resultMoney(response)
        })
    }

    private fun getAllCartUser(userId: String) {
        viewModels.getAllProductCart(userId, onComplete = { response ->
            productCartAdapter.addData(response)
            RecyclerUtils.setGridManager(
                requireContext(),
                binding.RCListSpCar,
                productCartAdapter
            )
            resultMoney(response)

        })

    }

    private fun deleteCart(productId : String, userId : String){
        viewModels.deleteCart(productId, userId, onComplete = { data ->
            if (data.status == true)
                confirmDialog.showDialogConfirm(getString(R.string.message_delete_cart_success))
        }, onErrors = {errorResponse ->
            confirmDialog.showDialogConfirm(getString(R.string.message_delete_cart_failure))
        })
    }

    private fun addCart(productId: String? = null, quantity: Int? = null, userId: String? = null) {
        var result = CartBody(productId, quantity, userId)
        viewModels.addProductIntoCart(result, onComplete = { data ->
//            confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_success))

        }, onErrors = { errorResponse ->
            confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_failure))
        })
    }


    @SuppressLint("SetTextI18n")
    private fun resultMoney(listSell: List<productList>) {
        var sumMoney = 0
        for (i in listSell.indices) {
            sumMoney += (listSell[i].quantity?.times(listSell[i].unitPrice!!)!!)
        }
        binding.btRefund.text = "Thanh Toán (${PriceHelper.getPriceFormat(sumMoney)}đ)"
//        Log.d("LINHZZZZZZZZZZZZZZZ", sumMoney.toString())
    }
}