package com.example.grocery_shop.customview.LoadView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.grocery_shop.databinding.LoadingViewBinding
import com.example.grocery_shop.util.singleClick

class LoadView : FrameLayout {

    private val binding =
        LoadingViewBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        binding.container.singleClick {
            binding.container.postDelayed({ binding.container.visibility = View.GONE }, 500L)
        }
    }

    fun showLoading() {
        binding.container.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.container.visibility = View.GONE
    }
}