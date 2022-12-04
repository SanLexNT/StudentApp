package com.example.studentapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.studentapp.domain.Student

interface StudentRepository {

    fun getStudents(): LiveData<List<Student>>

    suspend fun insertStudent(student: Student)


    suspend fun updateStudent(student: Student)


    suspend fun deleteStudent(student: Student)

}