package com.example.contactapp.room_component

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date


@TypeConverters
class DateConverter {
    @TypeConverter
    fun fromSource(source: Date) :Long{
        return source.time.toLong()
    }
    @TypeConverter
    fun  tvSource(source: Long): Date {
        return Date(source)
    }
}