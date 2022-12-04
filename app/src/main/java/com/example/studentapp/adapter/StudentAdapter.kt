package com.example.studentapp.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.studentapp.R
import com.example.studentapp.domain.Student
import com.example.studentapp.utils.MAIN

class StudentAdapter: Adapter<StudentAdapter.StudentViewHolder>(){

    private var students = emptyList<Student>()
    var onClickListener : ((student: Student) -> Unit) ?= null

    class StudentViewHolder(view: View) : ViewHolder(view){
        val student: TextView = view.findViewById(R.id.tv_student)
        val status: TextView = view.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.student.text = "${student.surname} ${student.name}"
        holder.status.text = MAIN.resources.getStringArray(R.array.status)[student.status]

        holder.itemView.setOnClickListener {
            onClickListener?.invoke(student)
        }

    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun setList(students: List<Student>){
        this.students = students
        notifyItemRangeChanged(0, itemCount)
    }

}