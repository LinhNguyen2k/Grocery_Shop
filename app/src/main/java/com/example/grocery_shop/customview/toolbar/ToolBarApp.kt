package com.example.grocery_shop.customview.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.grocery_shop.R
import com.example.grocery_shop.databinding.ToolbarAppBinding
import com.example.grocery_shop.util.singleClick

class ToolBarApp : LinearLayout {

    var onLeftClickListener: (() -> Unit)? = null
    var onRightClickListener: (() -> Unit)? = null

    private val binding =
        ToolbarAppBinding.inflate(LayoutInflater.from(context), this)

    constructor(context: Context?) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        attrs?.let {
            val obtain = context.obtainStyledAttributes(attrs, R.styleable.ToolBarApp)
            val title = obtain.getString(R.styleable.ToolBarApp_title)
            val iconLeft = obtain.getDrawable(R.styleable.ToolBarApp_drawable_start)
            val iconRight = obtain.getDrawable(R.styleable.ToolBarApp_drawable_end)
            val titleColor = obtain.getColor(R.styleable.ToolBarApp_title_color, 0)
            setTitle(title)
            if (iconLeft != null) {
                binding.imageLeft.visibility = View.VISIBLE
                binding.imageLeft.setImageDrawable(iconLeft)
            } else {
                binding.imageLeft.visibility = View.INVISIBLE
            }

            if (titleColor != 0) {
                binding.textTitle.setTextColor(titleColor)
            }

            binding.imageLeft.singleClick {
                onLeftClickListener?.invoke()
            }

            if (iconRight != null) {
                binding.imageRight.visibility = View.VISIBLE
                binding.imageRight.setImageDrawable(iconRight)
            } else {
                binding.imageRight.visibility = View.INVISIBLE
            }
            binding.imageRight.singleClick {
                onRightClickListener?.invoke()
            }
            obtain.recycle()
        }
    }

    fun setTitle(title: String?) {
        if (title.isNullOrEmpty()) {
            binding.textTitle.visibility = View.INVISIBLE
        } else {
            binding.textTitle.visibility = View.VISIBLE
            binding.textTitle.text = title
        }
    }

    fun setIconLeft(id: Int) {
        if (id < 0) {
            binding.imageLeft.visibility = View.INVISIBLE
        } else {
            binding.imageLeft.setImageResource(id)
            binding.imageLeft.visibility = View.VISIBLE
            binding.imageLeft.singleClick {
                onLeftClickListener?.invoke()
            }
        }
    }

    fun setIconRight(id: Int) {
        if (id < 0) {
            binding.imageRight.visibility = View.INVISIBLE
        } else {
            binding.imageRight.visibility = View.VISIBLE
            binding.imageRight.setImageResource(id)
        }
    }
}