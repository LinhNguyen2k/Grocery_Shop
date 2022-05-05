package com.example.grocery_shop.view.fragment.client

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.grocery_shop.adapter.ProductViewSearchAdapter
import com.example.grocery_shop.base.BaseFragment
import com.example.grocery_shop.base.RecyclerUtils
import com.example.grocery_shop.databinding.FragmentViewAllCategoryBinding
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.grocery_shop.view.activity.client.HomeActivity
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ViewAllCategoryFragment : BaseFragment<FragmentViewAllCategoryBinding, AuthenticationViewModel>() {
    private val viewModels by viewModels<AuthenticationViewModel>()
    private val categoryListOne by lazy {
        ProductViewSearchAdapter()
    }
    override fun initView() {
        val bundle = arguments
        getListProduct(bundle?.getString("setCategory").toString())

    }

    override fun initListener() {
        categoryListOne.onTrashClickListener = { data ->
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_PRODUCT_SELECTED, data)
            bundle.putString("category_key", bundle.getString("setCategory").toString())
            (requireActivity() as? HomeActivity?)?.let { activity ->
                activity.replaceFragmentFullScreen(DetailProductFragment().apply {
                    arguments = bundle
                }, true)
            }
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        var job: Job? = null
        binding.edtFilter.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    viewModels.getListSearch(
                        binding.edtFilter.text.toString(), onComplete = { data ->
                            categoryListOne.dataList = data as MutableList<productList>
                            categoryListOne.notifyDataSetChanged()


                        }, onErrors = {

                        })
                }
            }
        }
    }
    private fun getListProduct(id: String) {
        viewModels.getCategoryDetail(null, id, onComplete = { data ->
            categoryListOne.addMoreData(data)
            RecyclerUtils.setGridManager(
                requireContext(),
                binding.rcListViewAll,
                categoryListOne
            )
        })
    }


}