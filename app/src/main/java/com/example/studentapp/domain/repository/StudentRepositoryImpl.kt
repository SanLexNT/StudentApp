package com.example.studentapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.studentapp.domain.Student
import com.example.studentapp.domain.database.StudentDao

class StudentRepositoryImpl(private val dao: StudentDao) : StudentRepository {

    override fun getStudents(): LiveData<List<Student>> {
        return dao.getStudents()
    }

    override suspend fun insertStudent(student: Student) {
        dao.insertStudent(student)
    }

    override suspend fun updateStudent(student: Student) {
        dao.updateStudent(student)
    }

    override suspend fun deleteStudent(student: Student) {
        dao.deleteStudent(student)
    }


}