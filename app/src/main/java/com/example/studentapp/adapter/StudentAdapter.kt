package com.example.studentapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.studentapp.R
import com.example.studentapp.domain.Student

class StudentAdapter(private val context: Context): Adapter<StudentAdapter.StudentViewHolder>(){

    private var students = emptyList<Student>()
    var onClickListener : ((student: Student) -> Unit) ?= null
    var onStatusChangeListener: ((student: Student, spinnerPosition: Int) -> Unit) ?= null

    class StudentViewHolder(view: View) : ViewHolder(view){
        val student: TextView = view.findViewById(R.id.tv_student)
        val status: AutoCompleteTextView = view.findViewById(R.id.statusAc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.student.text = "${student.surname} ${student.name}"
        holder.status.setText(context.resources.getStringArray(R.array.status)[student.status])

        val adapter = ArrayAdapter(context, R.layout.dropdown_item,
            context.resources.getStringArray(R.array.status))
        holder.status.setAdapter(adapter)

        holder.itemView.setOnLongClickListener {
            onClickListener?.invoke(student)
            true
        }

        holder.status.setOnItemClickListener { _, _, i, _ ->

            onStatusChangeListener?.invoke(student, i)
            notifyItemChanged(position)
            holder.status.clearFocus()
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