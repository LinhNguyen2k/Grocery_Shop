package com.example.grocery_shop.view.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.grocery_shop.R
import com.example.grocery_shop.base.BaseActivity
import com.example.grocery_shop.databinding.ActivitySplashBinding
import com.mobile.mbccs.base.component.navigator.openActivity
import kotlinx.coroutines.delay

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun initView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        lifecycleScope.launchWhenCreated {
            delay(2000)
            openActivity(HomeActivity::class.java, true)
        }
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
    }
}