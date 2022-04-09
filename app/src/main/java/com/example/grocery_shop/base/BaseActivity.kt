package com.example.grocery_shop.base
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), View.OnClickListener {
    private val activityObserverList by lazy { mutableListOf<ActivityResultObserver>() }

    protected val binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launchWhenResumed {
            ActivityManager.addActivity(this@BaseActivity)
            initView()
            initListener()
            initData()
            if (BuildConfig.DEBUG) {
                println("SCREEN_APP ${this@BaseActivity::class.java.name}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityObserverList.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }

    fun addObserver(activityResultObserver: ActivityResultObserver) {
        activityObserverList.add(activityResultObserver)
    }

    fun removeObserver(activityResultObserver: ActivityResultObserver) {
        activityObserverList.remove(activityResultObserver)
    }

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
    }

}