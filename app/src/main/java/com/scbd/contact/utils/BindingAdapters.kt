package com.scbd.contact.utils

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scbd.contact.R
import com.scbd.contact.database.Contact
import com.scbd.contact.overview.ContactListAdapter

@BindingAdapter("listData")
fun bindRecycler(recyclerView: RecyclerView, contactsList: List<Contact>?) {
    if (null != contactsList && contactsList.isNotEmpty()) {
        val adapter = recyclerView.adapter as ContactListAdapter
        adapter.contactList = contactsList
    }
}

@BindingAdapter("image")
fun bindImage(imageTextView: ImageTextView, imageString: String?) {
    if (!imageString.isNullOrEmpty()&& imageString !="") {
        val bitmap = ImageBitmapString.stringToBitMap(imageString)
        bitmap?.let { imageTextView.setImage(bitmap) }
    }
}

@BindingAdapter("name")
fun bindText(imageTextView: ImageTextView, name: String?) {
    name?.let { imageTextView.setName(name) }
}