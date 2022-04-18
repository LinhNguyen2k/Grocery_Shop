package com.example.grocery_shop.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.mobile.mbccs.base.component.navigator.getGenericSuperclass

abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    open val viewModel: VM by lazy {
        ViewModelProvider(this)[getGenericSuperclass(1)]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }


}
