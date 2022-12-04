package com.example.studentapp.screens.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studentapp.R
import com.example.studentapp.databinding.FragmentAddBinding
import com.example.studentapp.domain.Student
import com.example.studentapp.utils.MAIN


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatusField()
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        setupStatusField()
    }


    override fun onStart() {
        super.onStart()

        viewModel.initDb()

        var statusId = 0

        binding.apply {

            statusAc.setOnItemClickListener { adapterView, view, i, l ->
                statusId = i
            }

            toolbarAdd.setOnMenuItemClickListener {
                if(it.itemId == R.id.item_done){
                    if(checkInputs(surnameEt.text.toString(), nameEt.toString())){
                        val surname = surnameEt.text.toString()
                        val name = nameEt.text.toString()
                        val student = Student(surname = surname, name = name, status = statusId)
                        viewModel.addStudent(student)
                        findNavController().popBackStack()
                    } else{
                        Toast.makeText(requireContext(), getString(R.string.error_text_fields),
                            Toast.LENGTH_SHORT).show()
                    }

                }
                true
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