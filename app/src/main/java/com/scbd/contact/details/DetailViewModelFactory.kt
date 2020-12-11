package com.scbd.contact.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scbd.contact.database.Contact
import com.scbd.contact.database.ContactDao
import java.lang.IllegalArgumentException

class DetailViewModelFactory(val database: ContactDao, val contact: Contact) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(database, contact) as T
        }

        throw IllegalArgumentException("Failed")
    }
}