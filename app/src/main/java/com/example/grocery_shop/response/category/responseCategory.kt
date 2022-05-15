package com.example.grocery_shop.response.category

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Keep
data class responseCategory(
    val categoryId: Int,
    val categoryName: String,
    val delete: Boolean,
    val productList:@RawValue List<Product>
): Parcelable