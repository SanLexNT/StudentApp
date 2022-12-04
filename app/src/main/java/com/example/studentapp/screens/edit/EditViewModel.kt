package com.example.studentapp.screens.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.domain.Student
import com.example.studentapp.domain.database.StudentDatabase
import com.example.studentapp.domain.repository.StudentRepository
import com.example.studentapp.domain.repository.StudentRepositoryImpl
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: StudentRepository
    private val context = application

    fun initDb(){
        val dao = StudentDatabase.getInstance(context).getDao()

        repository = StudentRepositoryImpl(dao)
    }

    fun editStudent(student: Student){
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }

    fun deleteStudent(student: Student){
        viewModelScope.launch {
            repository.deleteStudent(student)
        }
    }
}