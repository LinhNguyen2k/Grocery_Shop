package com.example.grocery_shop.view.fragment.client

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.example.grocery_shop.R
import com.example.grocery_shop.adapter.CategoryAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.databinding.FragmentHomeBinding
import com.example.grocery_shop.util.Constants.Companion.KEY_PRODUCT_SELECTED
import com.example.grocery_shop.view.activity.client.HomeActivity
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import com.squareup.picasso.Picasso
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val categoryListOne by lazy {
        CategoryAdapter()
    }
    private val categoryListTwo by lazy {
        CategoryAdapter()
    }
    private val categoryListThree by lazy {
        CategoryAdapter()
    }
    private val categoryListFour by lazy {
        CategoryAdapter()
    }
    private val categoryListFive by lazy {
        CategoryAdapter()
    }

    override fun initView() {
        getCategory()
        setlayout()
    }

    override fun initListener() {
        setLayoutViewAll()
        categoryListOne.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", "1")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        categoryListTwo.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", "2")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        categoryListThree.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", "3")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        categoryListFour.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", "4")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        categoryListFive.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", "5")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
    }

    override fun initData() {


    }

    private fun getCategory() {
        viewModels.getCategory(null, "1", onComplete = { data ->
            categoryListOne.addData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.rcProductSale,
                categoryListOne
            )
        }, onErrors = {

        })
        viewModels.getCategory(null, "2", onComplete = { data ->
            categoryListTwo.addMoreData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.rcVegetable,
                categoryListTwo
            )
        })
        viewModels.getCategory(null, "3", onComplete = { data ->
            categoryListThree.addMoreData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.rcFrozenFoods,
                categoryListThree
            )
        })
        viewModels.getCategory(null, "4", onComplete = { data ->
            categoryListFour.addMoreData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.rcFoods,
                categoryListFour
            )
        })
        viewModels.getCategory(null, "5", onComplete = { data ->
            categoryListFive.addMoreData(data)
            RecyclerUtils.setGridManagerH(
                requireContext(),
                binding.rcConfectionery,
                categoryListFive
            )
        })
    }


    private fun setlayout() {
        val slide = ArrayList<String>()
        slide.add("https://i.imgur.com/2pKMat0.jpg")
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

    private fun setLayoutViewAll() {
        binding.layoutViewAllSale.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("setCategory", "1")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(ViewAllCategoryFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        binding.layoutViewAllVegetable.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("setCategory", "2")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(ViewAllCategoryFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        binding.layoutViewAllFood.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("setCategory", "3")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(ViewAllCategoryFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        binding.layoutViewAllFrozenFoods.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("setCategory", "4")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(ViewAllCategoryFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
        binding.layoutViewAllConfectionery.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("setCategory", "5")
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(ViewAllCategoryFragment().apply {
                    arguments = bundle
                }, true)
            }
        }
    }

}