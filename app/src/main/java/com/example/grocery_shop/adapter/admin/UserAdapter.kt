package com.example.grocery_shop.adapter.admin

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseRecyclerViewAdapter
import com.example.grocery_shop.databinding.CustomAccountUserBinding
import com.example.grocery_shop.databinding.CustomItemViewAllOrderBinding
import com.example.grocery_shop.model.user.account.accountResponseItem
import com.example.grocery_shop.response.order.orderResponseManage
import java.text.SimpleDateFormat

class UserAdapter : BaseRecyclerViewAdapter<accountResponseItem, CustomAccountUserBinding>() {
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun bindData(binding: CustomAccountUserBinding, item: accountResponseItem, position: Int) {
        binding.tvEmail.text = "Email: " + item.email
        binding.tvPhoneNumber.text = "Số điện thoại: " + item.phone
        binding.tvDateOfBirthDay.text = "Ngày sinh: " + item.dateOfBirthday
        binding.tvRole.text = "Vai trò: " + item.role.name
    }
}
//
//try {
//    binding.tvNameUser.text = "Tên: " + item.fullName
//} catch (e: Exception) {
//
//}
//try {
//    Glide.with(context)
//        .load(item.avatar)
//        .error(R.drawable.avata)
//        .into(binding.imgUser)
//} catch (throwable: Throwable) {
//    throwable.printStackTrace()
//}