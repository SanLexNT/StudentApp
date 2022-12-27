package com.example.studentapp.screens.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.domain.Student
import com.example.studentapp.domain.database.StudentDatabase
import com.example.studentapp.domain.repository.StudentRepository
import com.example.studentapp.domain.repository.StudentRepositoryImpl
import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private var repository: StudentRepository

    init {
        val dao = StudentDatabase.getInstance(context).getDao()
        repository = StudentRepositoryImpl(dao)
    }

    fun addStudent(student: Student){
        viewModelScope.launch {
            repository.insertStudent(student)
        }
    }


}