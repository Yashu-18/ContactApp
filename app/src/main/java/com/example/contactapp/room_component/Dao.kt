package com.example.contactapp.room_component

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contactapp.table.contact


@Dao
interface Dao {

    @Query("Select * From contact_name")
    fun readdata():LiveData<List<contact>>

    @Insert(entity = contact::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertdata(contact: contact)

    @Update
    fun updatedata(contact: contact)

    @Delete
    fun deletedata(contact: contact)
}