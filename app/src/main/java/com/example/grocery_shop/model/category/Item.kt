package com.example.grocery_shop.model.category

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("categoryName")
    val categoryName: String? = null,
    @SerializedName("productList")
    val productList: List<productList>? = null,
    @SerializedName("delete")
    val delete: Boolean? = null
)
