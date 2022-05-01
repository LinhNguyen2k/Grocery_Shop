package com.example.grocery_shop.view.fragment

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.databinding.FragmentCustomerBinding
import com.example.grocery_shop.util.UserManager
import com.example.grocery_shop.util.singleClick
import com.example.grocery_shop.view.activity.EditProfileActivity
import com.example.grocery_shop.view.activity.LoginActivity
import com.example.grocery_shop.viewmodel.AuthenticationViewModel


class CustomerFragment : BaseFragment<FragmentCustomerBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()

    override fun initView() {
    }

    override fun initListener() {
        binding.imgChooseImg.singleClick {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(i)
        }
        binding.btnSignOut.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            UserManager.clearUserInfo(requireContext())
        }
    }

    override fun initData() {
        initInfoUser()
    }

    private fun initInfoUser() {
        viewModels.getInfoUser(
            UserManager.getUserId(requireContext()).toString(),
            onComplete = { data ->

                try {
                    Glide.with(requireContext())
                        .load(data.avatar.toString())
                        .error(R.drawable.iv_no_image)
                        .into(binding.imgAvatarUser)
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
                binding.edtUsernameInfor.setText(data.username)
                binding.txtFullName.text = data.fullName
                binding.txtEmail.text = data.email
                binding.txtPhone.text = data.phone

                UserManager.setUserName(requireContext(), data.username)
                UserManager.setFulLName(requireContext(), data.fullName)
                UserManager.setEmail(requireContext(), data.email)
                UserManager.setPhone(requireContext(), data.phone)

                Log.d("LIXXXXXXXXXXXXXXXXX1", UserManager.getFullName(requireContext()))
                Log.d("LIXXXXXXXXXXXXXXXXX2", UserManager.getUserName(requireContext()))
                Log.d("LIXXXXXXXXXXXXXXXXX3", UserManager.getEmail(requireContext()))
                Log.d("LIXXXXXXXXXXXXXXXXX4", UserManager.getPhone(requireContext()))
            })
    }

}