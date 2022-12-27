package com.example.studentapp.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var surname: String,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var status: String,

) : java.io.Serializable