package com.scbd.contact.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class OverViewViewModelFactory(val app: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverViewViewModel::class.java)) {
            return OverViewViewModel(app) as T
        }

        throw IllegalArgumentException("Unknown")
    }
}