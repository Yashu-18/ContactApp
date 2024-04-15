package com.example.contactapp.room_component

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.contactapp.table.contact


@TypeConverters(DateConverter::class)
@Database(entities = arrayOf(contact::class), version = 2, exportSchema = false)
abstract class MainDb:RoomDatabase() {

    abstract  fun dao(): Dao

    companion object{
        var db: MainDb?=null
        fun createDb(context: Context): MainDb {
            if(db ==null){
                db = Room.databaseBuilder(context, MainDb::class.java,"My_databse.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return db!!
        }
    }

}