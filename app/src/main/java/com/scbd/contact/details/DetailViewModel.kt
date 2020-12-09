package com.scbd.contact.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scbd.contact.datas.Contact

class DetailViewModel(app: Application, contacts: Contact) : ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    init {
        _contact.value = contacts
    }
}