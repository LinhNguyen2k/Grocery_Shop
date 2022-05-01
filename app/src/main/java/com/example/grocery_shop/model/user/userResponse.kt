package com.example.grocery_shop.model.user

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Keep
data class userResponse(
    val authProvider: @RawValue Any,
    val avatar: @RawValue Any,
    val cart: @RawValue List<Cart>,
    val dateOfBirthday: @RawValue Any,
    val delete: Boolean,
    val email: String,
    val fullName: String,
    val orders: @RawValue List<Any>,
    val password: String,
    val phone: String,
    val reviews: @RawValue List<Any>,
    val role: @RawValue Role,
    val userId: Int,
    val username: String
) : Parcelable