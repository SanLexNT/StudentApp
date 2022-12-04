package com.example.studentapp.screens.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.studentapp.domain.Student
import com.example.studentapp.domain.database.StudentDatabase
import com.example.studentapp.domain.repository.StudentRepository
import com.example.studentapp.domain.repository.StudentRepositoryImpl
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private lateinit var repository: StudentRepository

    fun initDb(){
        val dao = StudentDatabase.getInstance(context).getDao()
        repository = StudentRepositoryImpl(dao)
    }

    fun getStudents() : LiveData<List<Student>>{
        return repository.getStudents()
    }

}