package com.example.grocery_shop.view.fragment.client

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.adapter.admin.ReviewProductAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.PriceHelper
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.customview.diaglog.DialogConfirmV2
import com.example.grocery_shop.databinding.FragmentDetailProductBinding
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.review.bodyReview
import com.example.grocery_shop.sql.SQLite_User
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import java.text.NumberFormat
import java.util.*


class DetailProductFragment :
    BaseFragment<FragmentDetailProductBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private var orderId : String? = null
    private var userId : String? = null
    private val categoryListOne by lazy {
        CategoryAdapter()
    }

    private val categoryListReview by lazy {
        ReviewProductAdapter()
    }
    private val confirmDialog by lazy {
        DialogConfirmV2(requireContext())
    }
    var item: productList? = null
    override fun initView() {
        val bundle = arguments
        getInfoProduct(bundle)
        getListProduct(bundle?.getString("category_key").toString())
    }

    override fun onResume() {
        isLoading.value = false
        super.onResume()
    }

    override fun initListener() {
        val bundle = Bundle()
        item = bundle.getParcelable(Constants.KEY_PRODUCT_SELECTED)
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        setClickAddCart()
        setClickItem()
            binding.btComment.setOnClickListener {
                if (binding.edContent.text.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Bạn chưa nhập nội dung đánh giá",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                        if (UserManager.getUserId(requireContext()).toString() == userId ) {
                            postReviewProduct(
                                binding.edContent.text.toString(),
                                orderId!!.toInt(),
                                item?.productId!!,
                                5,
                                UserManager.getUserId(requireContext())
                            )

                        }
                    binding.edContent.setText("")
                }
            }
    }

    override fun initData() {
    }

    private fun getAllReviewProduct(productId: String) {
        viewModels.getReviewByProductId(productId, onComplete = { response ->
            response.forEach { data ->
                var count = 1
                if (UserManager.getUserId(requireContext()) == data.id.userId && count == 1) {
                    orderId = data.id.orderId.toString()
                    userId = data.id.userId.toString()
                }
            }

            categoryListReview.addData(response)
            RecyclerUtils.setGridManager(
                requireContext(),
                binding.rcComment,
                categoryListReview
            )

        })
    }

    private fun postReviewProduct(
        comments: String,
        orderId: Int,
        productId: Int,
        rating: Int,
        usedId: Int
    ) {
        val body = bodyReview(comments, orderId, productId, rating, usedId)
        viewModels.postReview(body, onComplete = { response ->
            getAllReviewProduct(response.id.productId.toString())
        }, onErrors = {
            Toast.makeText(requireContext(), "Lỗi server", Toast.LENGTH_SHORT).show()
        })
    }

    private fun checkReviewProduct(productId: String) {
        val sqliteUser = SQLite_User(requireActivity())
        if (sqliteUser.checkUser(productId)) {
            binding.edContent.visibility = View.VISIBLE
            binding.btComment.visibility = View.VISIBLE
        } else {
            binding.edContent.visibility = View.GONE
            binding.btComment.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getInfoProduct(bundle: Bundle?) {
        item = bundle?.getParcelable(Constants.KEY_PRODUCT_SELECTED)
        val iv: String = item?.productImage.toString()
        binding.tvItemTitle.text = item?.productName
        if (TextUtils.isEmpty(iv)) {
        } else {
            try {
                Glide.with(requireContext())
                    .load(iv)
                    .error(R.drawable.iv_no_image)
                    .into(binding.imgProduct)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
        checkReviewProduct(item?.productId.toString())
        binding.tvItemOldPrice.text = PriceHelper.getPriceFormat(item?.oldUnitPrice) + "đ"
        binding.tvItemOldPrice.paintFlags =  binding.tvItemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        getAllReviewProduct(item?.productId.toString())
        val local = Locale("vi", "VN")
        val numberFormat = NumberFormat.getInstance(local)
        val money: String = numberFormat.format(item?.unitPrice)
        binding.tvItemNewPrice.text = money + "đ"
        binding.tvItemContent.text = item?.descriptionProduct
        binding.tvQuantity.text = item?.quantity.toString()
        binding.tvTest1.text = item?.descriptionProduct
    }

    private fun getListProduct(id: String) {
        viewModels.getCategoryDetail(null, id, onComplete = { data ->
            categoryListOne.addMoreData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.RCmoreSPChay,
                categoryListOne
            )
        })
    }

    private fun setClickAddCart() {
        binding.addCart.setOnClickListener {
            addCart(
                item?.productId.toString(), 1,
                UserManager.getUserId(requireContext()).toString()
            )
        }
    }

    private fun setClickItem() {
        categoryListOne.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", bundle.getString("category_key").toString())
            arguments = bundle
            getInfoProduct(bundle)
        }

    }

    private fun addCart(productId: String? = null, quantity: Int? = null, userId: String? = null) {
        var result = CartBody(productId, quantity, userId)
        viewModels.addProductIntoCart(result, onComplete = { data ->
            confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_success))

        }, onErrors = { errorResponse ->
            confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_failure))
        })
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun observeAddCart(){
//        viewModels.resultAddCart.observe(this, androidx.lifecycle.Observer { data ->
//            if (data.id.userId != null) {
//                categoryListOne.notifyDataSetChanged()
//                confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_success))
//            } else {
//                confirmDialog.showDialogConfirm(getString(R.string.message_add_cart_failure))
//            }
//        })
//    }

}