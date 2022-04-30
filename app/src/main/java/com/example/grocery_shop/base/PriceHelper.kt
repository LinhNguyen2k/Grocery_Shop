package com.example.grocery_shop.base

import java.text.DecimalFormat

object PriceHelper {

    fun getPriceFormat(price: Int?): String {
        if (price == null) return "0"
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(price).replace(",",".")
    }
}