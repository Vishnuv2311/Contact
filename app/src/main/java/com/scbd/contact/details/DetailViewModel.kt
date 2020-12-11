package com.scbd.contact.details

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scbd.contact.database.Contact
import com.scbd.contact.database.ContactDao
import kotlinx.coroutines.launch

class DetailViewModel(val database: ContactDao, contacts: Contact) : ViewModel() {

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _navigateToOverView = MutableLiveData<Boolean>()
    val navigateToOverride: LiveData<Boolean>
        get() = _navigateToOverView

    init {
        _contact.value = contacts
    }

    private val _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: MutableLiveData<Bitmap>
        get() = _imageBitmap

    fun saveContact(contact: Contact) {
        viewModelScope.launch {
            database.insert(contact)
        }
    }

    fun updateImage(bitmap: Bitmap) {
        _imageBitmap.value = bitmap
    }

    fun navigateToOverview() {
        _navigateToOverView.value = true
    }

    fun navigateToOverViewComplete() {
        _navigateToOverView.value = null
    }

}