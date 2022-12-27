package com.example.studentapp.screens.edit

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studentapp.R
import com.example.studentapp.databinding.FragmentEditBinding
import com.example.studentapp.domain.Student
import com.example.studentapp.screens.list.ListFragment
import com.example.studentapp.utils.MAIN
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var viewModel: EditViewModel
    private lateinit var student: Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        student = arguments?.getSerializable(ListFragment.STUDENT_KEY) as Student
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
        viewModel.initDb()

        binding.includeLayout.apply {
            surnameEt.setText(student.surname)
            nameEt.setText(student.name)

            binding.btnSave.setOnClickListener {
                val surname = surnameEt.text.toString()
                val name = nameEt.toString()
                if (checkInputs(surname, name)) {
                    student.surname = surname
                    student.name = name
                    try {
                        viewModel.editStudent(student)
                        Toast.makeText(requireContext(),
                            getString(R.string.toast_student_edit), Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e(TAG, e.message.toString())
                    } finally {
                        dismiss()
                    }
                } else {
                    if (surname.isEmpty()) surnameEt.error =
                        resources.getString(R.string.error_text_fields)
                    else nameEt.error =
                        resources.getString(R.string.error_text_fields)
                }
            }
            binding.imDelete.setOnClickListener {

                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(resources.getString(R.string.alert_dialog_title))
                    .setMessage(resources.getString(R.string.delete_student_question))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.text_yes)){ _, _ ->
                        deleteStudent()
                        dismiss()
                    }
                    .setNegativeButton(resources.getString(R.string.text_no)){ _, _ ->
                    }.create()

                dialog.show()

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor
                    (android.R.color.holo_red_dark, null))
            }
        }
    }

    private fun deleteStudent(){
        try {
            viewModel.deleteStudent(student)
            Toast.makeText(requireContext(),
                getString(R.string.toast_student_delete), Toast.LENGTH_SHORT).show()
        } catch (e:Exception){
            Log.e(TAG, e.message.toString())
        }
    }

    private fun checkInputs(surname: String, name: String) =
        surname.isNotBlank() && name.isNotBlank()

    companion object{
        const val TAG = "tag"

        fun newInstance(student: Student): EditFragment {
            val fragment = EditFragment()
            val bundle = Bundle()
            bundle.putSerializable(ListFragment.STUDENT_KEY, student)
            fragment.arguments = bundle
            return fragment
        }
    }

}