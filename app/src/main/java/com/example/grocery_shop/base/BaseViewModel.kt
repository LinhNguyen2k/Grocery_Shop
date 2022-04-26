package com.example.grocery_shop.base

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grocery_shop.R
import com.example.grocery_shop.api.base.ErrorResponse
import com.example.grocery_shop.response.ErrorServer
import com.example.grocery_shop.viewmodel.MessageVM
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    val message by lazy { MutableLiveData<MessageVM>() }

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
                handleError(onError, throwable)
            }
        }.launchIn(viewModelScope)
    }

    private val disableLoading: java.lang.Runnable = Runnable { isLoading.value = false }

    fun handleError(
        onError: ((err: ErrorResponse) -> Unit)? = null,
        throwable: Throwable? = null
    ) {
        if (throwable != null) {
            when (throwable.cause) {
                is UnknownHostException,
                is java.net.ConnectException -> {
                    message.value =
                        MessageVM(400, context?.getString(R.string.message_error_unknown_host))
                }
                is SocketTimeoutException -> {
                    message.value =
                        MessageVM(400, context?.getString(R.string.message_error_socket_timeout))
                }
                is HttpException -> {
                    (throwable.cause as? HttpException)?.response()?.errorBody()?.string()
                        .let { body ->
                            if (!body.isNullOrEmpty()) {
                                try {
                                    if (onError != null && !body.isNullOrEmpty()) {
                                        onError.invoke(ErrorResponse(message = body))
                                    } else {
                                        val errorServer =
                                            Gson().fromJson(body, ErrorResponse::class.java)
                                        if (errorServer?.message != null) {
                                            message.value = MessageVM(errorServer.status, errorServer.message)
                                        }
                                    }
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                }
            }
        }
    }
}