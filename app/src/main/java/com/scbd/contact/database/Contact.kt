package com.scbd.contact.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val contactId: Long = 0L,
    val name: String,
    val phoneNo: String,
    val img: String,
    val emailId: String
) : Parcelable
