package com.example.grocery_shop.response.order

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Keep
data class orderResponseManage(
    val address: String,
    val createDate: String,
    val delete: Boolean,
    val fullName: @RawValue Any,
    val note: String,
    val orderDetailEntities: @RawValue List<OrderDetailEntity>,
    val orderId: Int,
    val phoneNumber: String,
    val reviewsEntities: @RawValue List<Any>,
    val shipperId: @RawValue Any,
    val statusOrder: String,
    val totalAmount: Int
) : Parcelable