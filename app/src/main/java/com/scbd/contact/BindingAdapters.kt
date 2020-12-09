package com.scbd.contact

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scbd.contact.datas.Contact
import com.scbd.contact.overview.ContactListAdapter

@BindingAdapter("listData")
fun bindRecycler(recyclerView: RecyclerView, contactsList: ArrayList<Contact>?) {
    if (null != contactsList) {
        val adapter = recyclerView.adapter as ContactListAdapter
        adapter.contactList = contactsList
    }
}

@BindingAdapter("image")
fun bindImage(imageView: ImageView, uri: Uri?) {
    if (null != uri) {
        imageView.setImageURI(uri)
    } else {
        imageView.setBackgroundResource(R.drawable.cirecle_1)
    }

}