package com.example.grocery_shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CartGetAllResponseItem(
    val cartEntities: @RawValue List<CartEntity>,
    val delete: Boolean,
    val descriptionProduct: String,
    val discount: Int,
    val oldUnitPrice: Int,
    val productId: Int,
    val productImage: String,
    val productName: String,
    val quantity: Int,
    val reviewsEntities: @RawValue List<Any>,
    val unitPrice: Int
) : Parcelable