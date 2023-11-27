package com.hokari.customer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Address(
    val userId: String = "",
    val fullName: String = "",
    val mobileNumber: String = "",
    val address: String = "",
    val zipCode: String = "",
    val additionalNote: String = "",
    val type: String = "",
    val otherDetails: String = "",
    var id: String = "",
) : Parcelable