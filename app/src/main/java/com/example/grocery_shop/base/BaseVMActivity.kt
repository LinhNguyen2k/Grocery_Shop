package com.example.grocery_shop.base

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.mobile.mbccs.base.component.navigator.getGenericSuperclass

abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {
    open val viewModel: VM by lazy {
        ViewModelProvider(this)[getGenericSuperclass(1)]
    }

    open fun handleLoading(isCancel: Boolean? = false) {
        try {
            if (isCancel != null) {
                loadingDialog.isCancelTouch = isCancel
            }
            viewModel.isLoading.observe { isShow ->
                if (isShow) {
                    if (!isFinishing && !loadingDialog.isAdded) {
                        loadingDialog.show(this@BaseVMActivity)
                    }
                } else {
                    if (!isFinishing) {
                        loadingDialog.dismissAllowingStateLoss()
                    }
                }
            }
        } catch (e: Exception) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        handleLoading()
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }


}
