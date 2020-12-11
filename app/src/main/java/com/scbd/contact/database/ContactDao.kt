package com.scbd.contact.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY name")
    fun getAllContact(): LiveData<List<Contact>>
}