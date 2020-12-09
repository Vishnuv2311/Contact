package com.scbd.contact.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scbd.contact.datas.Contact
import java.lang.IllegalArgumentException

class DetailViewModelFactory(val app: Application, val contact: Contact) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(app, contact) as T
        }

        throw IllegalArgumentException("Failed")
    }
}