package com.hokari.customer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Order(
    val user_id: String = "",
    val items: ArrayList<Cart> = ArrayList(),
    val address: Address = Address(),
    val title: String = "",
    val image: String = "",
    val sub_total_amount: String = "",
    val shipping_charge: String = "",
    val total_amount: String = "",
    var order_date: Long = 0L,
    var id: String = "",
) : Parcelable
