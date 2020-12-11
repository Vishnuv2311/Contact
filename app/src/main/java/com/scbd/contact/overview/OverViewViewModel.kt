package com.scbd.contact.overview

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.provider.ContactsContract
import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scbd.contact.database.Contact
import com.scbd.contact.database.ContactDao
import kotlinx.coroutines.*


class OverViewViewModel(val app: Application, val database: ContactDao) : ViewModel() {

    private val _navigateToSelectedContact = MutableLiveData<Contact>()
    val navigateToSelectedContact: LiveData<Contact>
        get() = _navigateToSelectedContact

    private val _navigateToAddContact = MutableLiveData<Boolean>()
    val navigateToAddContact: LiveData<Boolean>
        get() = _navigateToAddContact

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val contacts = database.getAllContact()

    fun displayContactDetails(contact: Contact) {
        _navigateToSelectedContact.value = contact
    }

    fun displayUserDetailsComplete() {
        _navigateToSelectedContact.value = null
    }

    fun displayAddContact() {
        _navigateToAddContact.value = true
    }

    fun navigationComplete() {
        _navigateToAddContact.value = null
        _navigateToSelectedContact.value = null
    }

    fun showTextMessage(status: Boolean) {
        _status.value = status
    }

    fun navigateToDetails(contact: Contact) {
        _navigateToSelectedContact.value = contact
    }

}