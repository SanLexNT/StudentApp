package com.example.studentapp.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentapp.domain.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun getDao(): StudentDao

    companion object{

        private var instance: StudentDatabase ?= null

        @Synchronized
        fun getInstance(context: Context) : StudentDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, StudentDatabase::class.java, "studentdb")
                    .build()
            }
            return instance as StudentDatabase
        }
    }
}