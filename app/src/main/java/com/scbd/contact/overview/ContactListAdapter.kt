package com.scbd.contact.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scbd.contact.databinding.ContcatItemBinding
import com.scbd.contact.database.Contact

class ContactListAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    var contactList = listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ContcatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.contact = contact
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContcatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.itemView.setOnClickListener { onClickListener.onClick(contact) }
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}

class OnClickListener(val clickListener: (contact: Contact) -> Unit) {
    fun onClick(contact: Contact) = clickListener(contact)
}