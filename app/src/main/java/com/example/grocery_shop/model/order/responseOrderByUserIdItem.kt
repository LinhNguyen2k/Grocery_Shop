package com.example.grocery_shop.model.order

data class responseOrderByUserIdItem(
    val address: String,
    val createDate: String,
    val delete: Boolean,
    val fullName: String,
    val note: String,
    val orderDetailEntities: List<OrderDetailEntity>,
    val orderId: Int,
    val phoneNumber: String,
    val reviewsEntities: List<Any>,
    val shipperId: Any,
    val statusOrder: String,
    val totalAmount: Int
)