package com.example.studentapp.screens.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.studentapp.R
import com.example.studentapp.databinding.FragmentAddBinding
import com.example.studentapp.domain.Student
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        binding.includeLayout.apply {

            binding.btnAdd.setOnClickListener{
                val surname = surnameEt.text.toString()
                val name = nameEt.text.toString()
                if(checkInputs(surname, name)){
                    val student = Student(surname = surname, name = name,
                        status = resources.getStringArray(R.array.status)[0])
                    try {
                        viewModel.addStudent(student)
                        Toast.makeText(requireContext(), getString(R.string.student_add),
                            Toast.LENGTH_SHORT).show()
                    } catch (e:Exception){
                        Log.e(TAG, e.message.toString())
                    } finally {
                        dismiss()
                    }
                } else{
                    if(surname.isEmpty()) surnameEt.error = resources.getString(R.string.error_text_fields)
                    else nameEt.error = resources.getString(R.string.error_text_fields)
                }
            }
        }
    }

    private fun checkInputs(surname: String, name: String) : Boolean{
        return surname.isNotBlank() && name.isNotBlank()
    }

    companion object{
        const val TAG = "Tag"
    }

}