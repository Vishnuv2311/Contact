package com.scbd.contact.overview

import android.app.Application
import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scbd.contact.datas.Contact


class OverViewViewModel(val app: Application) : ViewModel() {

    private val _contactList = MutableLiveData<ArrayList<Contact>>()

    val contactList: LiveData<ArrayList<Contact>>
        get() = _contactList

    private val _navigateToSelectedContact = MutableLiveData<Contact>()

    val navigateToSelectedContact: LiveData<Contact>
        get() = _navigateToSelectedContact


    init {
        fetchContacts()
    }

    private fun fetchContacts() {
        val contactList = arrayListOf<Contact>()
        val cr: ContentResolver = app.contentResolver
        val cur = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if (cur?.count ?: 0 > 0) {
            while (cur!!.moveToNext()) {
                val id = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            val phoneNo = pCur.getString(
                                pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            contactList.add(Contact(name, phoneNo))
                        }
                        pCur.close()
                    }
                }
            }
        }
        cur?.close()
        _contactList.value = contactList
    }

    fun displayContactDetails(contact: Contact) {
        _navigateToSelectedContact.value = contact
    }

    fun displayUserDetailsComplete() {
        _navigateToSelectedContact.value = null
    }
}