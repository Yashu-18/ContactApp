package com.example.contactapp.viewModel

import android.app.Application
import android.provider.ContactsContract.Intents.Insert
import androidx.lifecycle.AndroidViewModel
import com.example.contactapp.UI.Repository
import com.example.contactapp.room_component.Dao
import com.example.contactapp.room_component.MainDb
import com.example.contactapp.table.contact

class addupdate( var app: Application):AndroidViewModel(app){

    lateinit var dao: Dao
    lateinit var repository: Repository
    init {
        dao = MainDb.createDb(app).dao()
        repository=Repository(dao)
    }

    fun insertData(contact: contact,afterInsert:()->Unit){
        repository.insertdata(contact)
        afterInsert()
    }

    fun updateData(contact: contact,afterInsert:()->Unit){
        repository.updatedata(contact)
        afterInsert()
    }

}