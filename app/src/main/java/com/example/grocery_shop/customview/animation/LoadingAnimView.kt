package com.example.grocery_shop.customview.animation

import android.content.Context
import android.util.AttributeSet
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class LoadingAnimView: LottieAnimationView {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        repeatCount = LottieDrawable.INFINITE
        setAnimation("lottie/loader.json")
    }

    fun start() {
        playAnimation()
    }

    fun stop() {
        cancelAnimation()
    }
}