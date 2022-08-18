package com.example.grocery_shop.view.fragment.client

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.FragmentAddInfoUserBinding
import com.example.grocery_shop.model.order.orderBody
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.view.activity.client.HomeActivity
import com.example.grocery_shop.viewmodel.AuthenticationViewModel

class AddInfoUserFragment : BaseFragment<FragmentAddInfoUserBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    val option = arrayOf("Thanh toán khi nhận hàng", "Thanh toán qua thẻ")
    var methodPay: String = ""
    private val confirmDialog by lazy {
        DialogConfirmV2(requireContext())
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        binding.btnConfirm.setOnClickListener {
            if (binding.edAddress.text.isEmpty() || methodPay.isEmpty() || binding.edPhoneNumber.text.isEmpty()){
                confirmDialog.showDialogConfirm("Bạn cần nhập đầy đủ thông tin")
            } else {

                orderClient(binding.edAddress.text.toString(), methodPay, binding.edPhoneNumber.text.toString())
            }
        }
        setUpSpinner()
    }

    override fun initData() {
    }


    private fun setUpSpinner() {
        binding.spTypeProblem.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            option
        )
        binding.spTypeProblem.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                methodPay = option[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

        }
    }

    private fun orderClient(
        address: String,
        note: String,
        phoneNumber: String
    ) {
        val result = orderBody(address, note, UserManager.getFullName(requireContext()), phoneNumber)
        viewModels.orderClient(
            UserManager.getUserId(requireContext()).toString(),
            result,
            onComplete = {
                val bundle = Bundle()
                bundle.putParcelable("info_order", it)
                (requireActivity() as? HomeActivity?)?.let { activity ->
                    activity.replaceFragmentFullScreen(OrderFragment().apply {
                        arguments = bundle
                    }, true)
                }
            }, onErrors = {
                confirmDialog.showDialogConfirm("Thanh toán không thành công")
            })
    }
}