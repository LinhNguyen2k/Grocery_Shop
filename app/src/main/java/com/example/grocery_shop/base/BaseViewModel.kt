package com.example.grocery_shop.base

import androidx.lifecycle.DefaultLifecycleObserver
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

    private val viewModelJob = SupervisorJob()

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
                                it.isTokenExpire = response.code() == 401
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
        onError: ((err: ErrorResponse?) -> Unit)? = null,
        onNext: (T) -> Unit,
    ) {
        this@subscribe.onStart {
        }.onEach {
            if (it != null) {
                withContext(Dispatchers.Main) {
                    onNext.invoke(it)
                }
            }
        }.catch {
            Throwable(it).also { throwable ->
                handleError(throwable, onError)
                throwable.printStackTrace()
            }
        }.launchIn(viewModelScope)
    }
}