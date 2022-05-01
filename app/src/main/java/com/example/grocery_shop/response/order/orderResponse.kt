package com.example.grocery_shop.response.order

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Keep
data class orderResponse(
    val address: String,
    val createDate: String,
    val delete: Boolean,
    val note: String,
    val orderDetailEntities:@RawValue Any,
    val orderId: Int,
    val phoneNumber: String,
    val reviewsEntities:@RawValue Any,
    val shipperId:@RawValue Any,
    val statusOrder: String,
    val totalAmount: Int
) : Parcelable