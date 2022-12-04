package com.example.studentapp.screens.edit

import android.os.Bundle
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


class EditFragment : Fragment() {

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

        binding.apply {
            surnameEtEdit.setText(student.surname)
            nameEtEdit.setText(student.name)
            statusAc.setText(resources.getStringArray(R.array.status)[student.status])

            setupStatusField()

            toolbarEdit.setOnMenuItemClickListener {


                when(it.itemId){
                    R.id.item_done ->{
                        if(checkInputs(surnameEtEdit.text.toString(), nameEtEdit.toString())){
                            student.surname = surnameEtEdit.text.toString()
                            student.name = nameEtEdit.text.toString()

                            viewModel.editStudent(student)
                            findNavController().popBackStack()
                        } else{
                            Toast.makeText(requireContext(), getString(R.string.error_text_fields),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {
                        viewModel.deleteStudent(student)
                        findNavController().popBackStack()
                    }
                }
                true
            }

            statusAc.setOnItemClickListener { _, _, i, _ ->
                student.status = i
            }
        }

        MAIN.onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }

            })

    }

    private fun setupStatusField(){
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,
            resources.getStringArray(R.array.status))
        binding.statusAc.setAdapter(adapter)
    }

    private fun checkInputs(surname: String, name: String) : Boolean{
        return surname.isNotBlank() && name.isNotBlank()
    }
}