package com.example.grocery_shop.model.category

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Keep
data class productList(
    @SerializedName("productId")
    val productId: Int? = null,
    @SerializedName("productName")
    val productName: String? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("productImage")
    val productImage: String? = null,
    @SerializedName("discount")
    val discount: Int? = null,
    @SerializedName("unitPrice")
    val unitPrice: Int? = null,
    @SerializedName("oldUnitPrice")
    val oldUnitPrice: Int? = null,
    @SerializedName("descriptionProduct")
    val descriptionProduct: String? = null,
    @SerializedName("cartEntities")
    var cartEntities: @RawValue List<Any>? = null,
    @SerializedName("reviewsEntities")
    val reviewsEntities:  @RawValue List<Any>?,
    @SerializedName("delete")
    val delete: Boolean? = null
) : Parcelable