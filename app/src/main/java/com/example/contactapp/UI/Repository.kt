package com.example.contactapp.UI

import androidx.lifecycle.LiveData
import com.example.contactapp.room_component.Dao
import com.example.contactapp.table.contact

class Repository(var dao: Dao) {

    fun readdata():LiveData<List<contact>> = dao.readdata()

    fun insertdata(contact: contact)=dao.insertdata(contact)

    fun deletedata(contact: contact)=dao.deletedata(contact)

    fun updatedata(contact: contact)=dao.updatedata(contact)
}