package com.example.contactapp.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "contact_name")
data class contact (
    @PrimaryKey(autoGenerate = true) var id:Int?=null,

    var name:String,

    var number:String,

    var email:String,

    var dob:Date?=null,

    var profileImage:ByteArray?=null


    ):Serializable