package com.example.grocery_shop.view.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.grocery_shop.R
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.databinding.FragmentHomeBinding
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.squareup.picasso.Picasso
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val categoryList by lazy {
        CategoryAdapter()
    }

    override fun initView() {
        setlayout()
        setupDetailChannelAdapter()
    }

    override fun initListener() {
    }

    override fun initData() {
        getCategory(0, 1)

    }
//null kia, chay di ban, call ve null kia
    private fun getCategory(page: Int? = null, category: Int? = null) {
        viewModels.getCategory(page, category, onComplete = { data ->
            categoryList.addData(data)
            Log.d("LINHHHHHHNAAAAAAAA", "${data} ")

        })

    }

    private fun setupDetailChannelAdapter() {
        RecyclerUtils.setGridManagerH(
            requireContext(),
            binding.rcProductSale,
            categoryList
        )
    }

    private fun setlayout() {
        val slide = ArrayList<String>()
        slide.add("https://i.imgur.com/SGuuvNg.jpg")
        slide.add("https://i.imgur.com/NZMXeby.jpg")
        val picasso = Picasso.get()
        for (i in slide.indices) {
            val imageView = ImageView(context)
            picasso.load(slide[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            binding.Viewflipper.addView(imageView)
        }
        binding.Viewflipper.flipInterval = 4000
        binding.Viewflipper.isAutoStart = true
        @SuppressLint("ResourceType") val animation_slide_in = AnimationUtils.loadAnimation(
            context, R.transition.slide_in_right
        )
        @SuppressLint("ResourceType") val animation_slide_out = AnimationUtils.loadAnimation(
            context, R.transition.slide_out_right
        )
        binding.Viewflipper.inAnimation = animation_slide_in
        binding.Viewflipper.outAnimation = animation_slide_out
    }

}