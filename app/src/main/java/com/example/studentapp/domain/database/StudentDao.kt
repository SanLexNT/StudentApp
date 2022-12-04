package com.example.studentapp.domain.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studentapp.domain.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    fun getStudents(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

}