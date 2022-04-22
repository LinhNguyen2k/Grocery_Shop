package com.example.grocery_shop.base

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grocery_shop.api.base.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    protected val context by lazy { ActivityManager.getTopActivity() }
    private val thread by lazy { Handler(Looper.getMainLooper()) }
    private val viewModelJob = SupervisorJob()
    val isLoading by lazy { MutableLiveData<Boolean>() }
    private val viewModelScope: CoroutineScope =
        CoroutineScope(viewModelJob + Dispatchers.Main.immediate)

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        handleError(exception)
    }

    private fun handleError(
        throwable: Throwable? = null,
        onError: ((err: ErrorResponse?) -> Unit)? = null,
    ) {

        if (throwable != null) {
            when (throwable.cause) {
                is UnknownHostException,
                is java.net.ConnectException -> {

                }
                is SocketTimeoutException -> {

                }
                is HttpException -> {
                    (throwable.cause as? HttpException)?.response()?.let { response ->
                        response.errorBody()?.string()?.let { body ->
                            val error: ErrorResponse? =
                                Gson().fromJson(body, ErrorResponse::class.java)
                            error?.let {
//                                it.isTokenExpire = response.code() == 401
                                onError?.invoke(it)
                            }
                        }
                    }
                }
            }
        }
    }

    protected fun launchHandler(
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(handler) {
            block()
        }
    }
    fun <T> flowOnIO(value: T) = flow {
        emit(value)
    }.flowOn(Dispatchers.Default)
    fun <T> Flow<T>.subscribe(
        onLoading: Boolean = true,
        keepLoading: Boolean = false,
        onError: ((err: ErrorResponse?) -> Unit)? = null,
        onNext: (T) -> Unit
    ) {
        this@subscribe.onStart {
            isLoading.value = onLoading
        }.onEach {
            if (it != null) {
                withContext(Dispatchers.Main) {
                    onNext.invoke(it)
                }
            }
            if (!keepLoading && onLoading) {
                thread.postDelayed(disableLoading, 500)
            }
        }.catch {
            isLoading.value = false
            Throwable(it).also { throwable ->
                throwable.printStackTrace()
            }
        }.launchIn(viewModelScope)
    }

    private val disableLoading: java.lang.Runnable = Runnable { isLoading.value = false }
}