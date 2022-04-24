package com.example.grocery_shop.model.category

import com.google.gson.annotations.SerializedName

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
    val cartEntities: List<Any>? = null,
    @SerializedName("reviewsEntities")
    val reviewsEntities: List<Any>? = null,
    @SerializedName("delete")
    val delete: Boolean? = null
)