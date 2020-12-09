package com.scbd.contact.datas

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val name: String,
    val phoneNo: String,
    val img : Uri
) : Parcelable
