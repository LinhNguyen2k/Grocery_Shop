package com.example.grocery_shop.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

fun <T : View> T.click(action: (T) -> Unit) {
    setOnClickListener {
        action(this)
    }
}

fun <T : View> T.singleClick(interval: Long = 500L, action: ((T) -> Unit)?) {
    setOnClickListener(SingleClickListener(interval, action))
}

class SingleClickListener<T : View>(
    private val interval: Long = 500L,
    private var clickFunc: ((T) -> Unit)?
) : View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - lastClickTime > interval) {
            if (clickFunc != null) {
                clickFunc!!(v as T)
            }
            lastClickTime = nowTime
        }
    }
}

fun Context.getScreenWidth(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun Activity.hideSystemUI(color: Int) {
    val decorView: View = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val wic = decorView.windowInsetsController
        wic?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    window.statusBarColor = color
}


fun Activity.hideSystemUIDark(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags: Int = window.decorView.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags
    }
    window.statusBarColor = color
}

fun Activity.fullStatusBar() {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.statusBarColor = Color.TRANSPARENT
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

fun View.getPositionView(onAxis: ((x: Int, y: Int) -> Unit)? = null) {
    val location = IntArray(2)
    getLocationOnScreen(location)
    onAxis?.invoke(location.first(), location.last())
}

//fun RecyclerView.onPositionScrollListener(onScrolled: (position: Int) -> Unit) {
//    addOnScrollListener(object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager?
//            layoutManager?.findLastVisibleItemPosition()?.let {
//                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
//                    onScrolled.invoke(it.plus(1))
//                } else {
//                    onScrolled.invoke(it)
//                }
//            }
//        }
//    })
//}

fun RecyclerView.onPositionScrollListener(onScrolled: (position: Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val offset = recyclerView.computeHorizontalScrollOffset()
            val myCellWidth = recyclerView.getChildAt(0).measuredWidth
            if (offset % myCellWidth == 0) {
                val position = offset / myCellWidth
                onScrolled.invoke(position)
            }
        }
    })
}

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun TextView.isSpannableCenter() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.isSpannableUnder() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun EditText.setMaxLength(maxLength: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
}

fun getInputInt(input: Editable?): Int {
    return input.toString().toIntOrNull() ?: 0
}
