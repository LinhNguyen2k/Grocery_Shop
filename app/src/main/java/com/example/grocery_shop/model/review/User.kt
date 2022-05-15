package com.example.grocery_shop.model.review

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val userId : String,
    val orderId : String,
) : Parcelable