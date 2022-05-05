package com.example.grocery_shop.viewmodel

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.grocery_shop.api.base.ErrorResponse
import com.example.grocery_shop.api.client.ApiClient
import com.example.grocery_shop.api.services.ProductService
import com.example.grocery_shop.base.BaseViewModel
import com.example.grocery_shop.base.file.FileUtil
import com.example.grocery_shop.model.auth.LoginBody
import com.example.grocery_shop.model.auth.SignBody
import com.example.grocery_shop.model.auth.bodyLoginGithub
import com.example.grocery_shop.model.auth.setNewPassBody
import com.example.grocery_shop.model.cart.CartBody
import com.example.grocery_shop.model.cart.CartResponse
import com.example.grocery_shop.model.category.productList
import com.example.grocery_shop.model.order.orderBody
import com.example.grocery_shop.model.product.bodyEditProduct
import com.example.grocery_shop.model.product.productAddBody
import com.example.grocery_shop.model.user.UserEditBody
import com.example.grocery_shop.model.user.account.accountResponseItem
import com.example.grocery_shop.model.user.infoUser.getUserById
import com.example.grocery_shop.model.user.userResponse
import com.example.grocery_shop.repository.LoginRepository
import com.example.grocery_shop.response.ForGotPassWordResponse
import com.example.grocery_shop.response.LoginResponse
import com.example.grocery_shop.response.auth.responseDeleteProduct
import com.example.grocery_shop.response.auth.responseNewPass
import com.example.grocery_shop.response.order.orderResponse
import com.example.grocery_shop.response.order.orderResponseManage
import com.example.grocery_shop.response.order.responseAllOrder
import com.example.grocery_shop.response.product.responseEditProduct
import com.example.grocery_shop.response.product.responseManageProduct
import com.example.grocery_shop.response.responseDeleteCart
import com.octalsoftaware.myapplication.utils.image.Compressor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class AuthenticationViewModel : BaseViewModel() {
    private val authenticationRepository by lazy {
        LoginRepository()
    }
    var imageFile: File? = null
    private val apiClient by lazy {
        ApiClient.createService(ProductService::class.java)
    }
    val resultSignUp by lazy {
        MutableLiveData<LoginResponse>()
    }

    val resultAddCart by lazy {
        MutableLiveData<CartResponse>()
    }

    fun signUp(
        avatar: String? = null,
        email: String,
        fullName: String,
        phone: String,
        username: String
    ) {
        val signBody = SignBody(avatar, email, fullName, phone, username)
        launchHandler {
            authenticationRepository.signUpWithAccount(signBody).subscribe(onNext = { response ->
                resultSignUp.postValue(response)
            }, onError = { error ->
                error?.let { response ->
                    Toast.makeText(
                        context,
                        "${response.status}  ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })
        }
    }

    fun login(
        password: String, username: String, onComplete: (response: LoginResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        val loginBody = LoginBody(password, username)
        isLoading.value = true
        launchHandler {
            authenticationRepository.loginUpWithAccount(loginBody).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })

        }

    }

    fun forGotPassWord(
        username: String,
        onComplete: (response: ForGotPassWordResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.forGotPassWord(username).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })

        }
    }

    fun getCategory(
        page: String? = null,
        category: String? = null,
        onComplete: (response: List<productList>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = true
        launchHandler {
            authenticationRepository.getCategory(page, category).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun getCategoryDetail(
        page: String? = null,
        category: String? = null,
        onComplete: (response: List<productList>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = false
        launchHandler {
            authenticationRepository.getCategory(page, category).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false

            }, onError = { err ->
                onErrors?.invoke(err)
                isLoading.value = false
            })
        }
    }

    fun addProductIntoCart(
        request: CartBody,
        onComplete: (response: CartResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.addProductIntoCart(request)
                .subscribe(onNext = { cartResponse ->
                    onComplete.invoke(cartResponse)

                })
        }

    }

    fun getAllProductCart(
        userId: String,
        onComplete: (response: List<productList>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = true
        launchHandler {
            authenticationRepository.getAllProductCart(userId).subscribe(onNext = { cartResponse ->
                onComplete.invoke(cartResponse)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
                isLoading.value = false
            })
        }

    }

    fun deleteCart(
        productId: String,
        userId: String,
        onComplete: (response: responseDeleteCart) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.deleteCart(productId, userId)
                .subscribe(onNext = { responseDelete ->
                    onComplete.invoke(responseDelete)
                }, onError = { err ->
                    onErrors?.invoke(err)
                })
        }
    }

    fun getInfoUser(
        id: String,
        onComplete: (response: getUserById) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = true
        launchHandler {
            authenticationRepository.getInfoUser(id).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun editProfile(
        id: String,
        body: UserEditBody,
        onComplete: (response: userResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.editProfile(id, body).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun setNewPassWord(
        body: setNewPassBody,
        onComplete: (response: responseNewPass) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.setNewPassWord(body).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }


    fun orderClient(
        id: String,
        body: orderBody,
        onComplete: (response: orderResponse) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = true
        launchHandler {
            authenticationRepository.orderClient(id, body).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun getListSearch(
        key: String,
        onComplete: (response: List<productList>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = false
        launchHandler {
            authenticationRepository.getListSearch(key).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun getListOrder(
        onComplete: (response: List<orderResponseManage>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.getListOrder().subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun deleteOrder(
        token: String,
        key: String,
        onComplete: (response: responseDeleteProduct) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.deleteOrder(token, key).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }


    fun deleteProduct(
        token: String,
        key: String,
        onComplete: (response: responseDeleteProduct) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        isLoading.value = false
        launchHandler {
            authenticationRepository.deleteProduct(token, key).subscribe(onNext = { response ->
                onComplete.invoke(response)
                isLoading.value = false
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }


    fun addProduct(
        token: String,
        key: String,
        body: productAddBody,
        onComplete: (response: responseManageProduct) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.addProduct(token, key, body).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun compressorImageProduct(
        token: String,
        id: String,
        intent: Intent,
        onComplete: (bitmap: Bitmap) -> Unit
    ) {
        try {
            imageFile = FileUtil.from(context, intent.data)?.also { file ->
                launchHandler {
                    context?.let { context ->
                        imageFile = Compressor.compress(context, file)
                        onComplete.invoke(BitmapFactory.decodeFile(imageFile?.absolutePath))
                        updateImageProduct(token, id)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun updateImageProduct(token: String, id: String) {
        imageFile?.asRequestBody("multipart/form-data".toMediaType())
            ?.also { body ->
                MultipartBody.Part.createFormData("avt", imageFile?.name, body).let { part ->
                    launchHandler {
                        flowOnIO(apiClient.addImage(token, id, part))
                            .subscribe(onNext = { response ->

                            }, onError = { error ->
                            })
                    }
                }
            }
    }


    fun compressorImage(id: String, intent: Intent, onComplete: (bitmap: Bitmap) -> Unit) {
        try {
            imageFile = FileUtil.from(context, intent.data)?.also { file ->
                launchHandler {
                    context?.let { context ->
                        imageFile = Compressor.compress(context, file)
                        onComplete.invoke(BitmapFactory.decodeFile(imageFile?.absolutePath))
                        updateImageProfile(id)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateImageProfile(id: String) {
        imageFile?.asRequestBody("multipart/form-data".toMediaType())
            ?.also { body ->
                MultipartBody.Part.createFormData("avt", imageFile?.name, body).let { part ->
                    launchHandler {
                        flowOnIO(apiClient.editProfileImage(id, part))
                            .subscribe(onNext = { response ->

                            }, onError = { error ->
                            })
                    }
                }
            }
    }

    fun getAllAccount(
        token: String,
        onComplete: (response: List<accountResponseItem>) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.getAllAccount(token).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }
    fun editInfoProduct(
        token: String,
        id: String,
        body: bodyEditProduct,
        onComplete: (response: responseEditProduct) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.editInfoProduct(token, id, body).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }



    fun getAllOrder(
        token: String,
        year: String,
        category: String?,
        onComplete: (response: responseAllOrder) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.getAllOrder(token, year, category).subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }

    fun getTokenGithub(
        onComplete: (response: Unit) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.getTokenGithub().subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }
    fun saveUserWhenLoginGithub(
        onComplete: (response: bodyLoginGithub) -> Unit,
        onErrors: ((ErrorResponse?) -> Unit)? = null
    ) {
        launchHandler {
            authenticationRepository.saveUserWhenLoginGithub().subscribe(onNext = { response ->
                onComplete.invoke(response)
            }, onError = { err ->
                onErrors?.invoke(err)
            })
        }
    }
}
