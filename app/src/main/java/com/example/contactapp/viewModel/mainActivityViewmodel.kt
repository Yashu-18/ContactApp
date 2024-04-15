package com.example.contactapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contactapp.UI.Repository
import com.example.contactapp.room_component.Dao
import com.example.contactapp.room_component.MainDb
import com.example.contactapp.table.contact


class mainActivityViewmodel(app:Application):AndroidViewModel(app) {
    lateinit var dao: Dao
    lateinit var repository: Repository
    lateinit var contactList:LiveData<List<contact>>
    init {
        dao = MainDb.createDb(app).dao()
        repository= Repository(dao)
        contactList=repository.readdata()
    }

    fun delete(contact: contact) {
        dao.deletedata(contact)
    }



}