package com.scbd.contact

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
