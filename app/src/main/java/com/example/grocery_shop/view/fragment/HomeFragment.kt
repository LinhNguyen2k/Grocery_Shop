package com.example.grocery_shop.view.fragment

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.databinding.FragmentHomeBinding
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.squareup.picasso.Picasso
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding, AuthenticationViewModel>() {


    override fun initView() {
        setlayout()
    }

    override fun initListener() {
    }

    override fun initData() {
    }
    private fun setlayout() {
        val slide = ArrayList<String>()
        slide.add("https://cdn-crownx.winmart.vn/images/prod/b%C3%A1nh%20k%E1%BA%B9o_1180x400-38_0b1a903b-11dd-4545-b60f-7a6e886a12d2.jpg")
        slide.add("https://cdn-crownx.winmart.vn/images/prod/rau củ thịt_1180x400-02_f4fb5d58-9253-4fb0-bde1-6357cc88d3e8.jpg")
        slide.add("https://cdn-crownx.winmart.vn/images/prod/hóa phẩm_1180x400 copy 3_0422270d-cf1e-4fcf-83dd-921438512da3.jpg")
        slide.add("https://cdn-crownx.winmart.vn/images/prod/rau củ thịt_1180x400-02_f4fb5d58-9253-4fb0-bde1-6357cc88d3e8.jpg")
        slide.add("https://cdn-crownx.winmart.vn/images/prod/bánh kẹo_1180x400-38_0b1a903b-11dd-4545-b60f-7a6e886a12d2.jpg")
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