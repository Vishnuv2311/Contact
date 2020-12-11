package com.scbd.contact.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDataBase : RoomDatabase() {
    abstract val contactDao: ContactDao
    companion object {
        @Volatile
        private var INSTANCE: ContactDataBase? = null

        fun getInstance(context: Context): ContactDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDataBase::class.java,
                        "contact_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}