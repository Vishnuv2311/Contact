package com.scbd.contact.datas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val name: String,
    val phoneNo: String
) : Parcelable
