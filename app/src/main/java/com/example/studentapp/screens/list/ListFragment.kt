package com.example.studentapp.screens.list

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentapp.R
import com.example.studentapp.adapter.StudentAdapter
import com.example.studentapp.adapter.WrapContentLinearLayoutManager
import com.example.studentapp.databinding.FragmentListBinding
import com.example.studentapp.domain.Student
import com.example.studentapp.screens.add.AddFragment
import com.example.studentapp.screens.edit.EditFragment

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        studentAdapter = StudentAdapter(requireContext())
        setupToolbar()

        with(binding.rvStudent) {

            layoutManager = WrapContentLinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            adapter = studentAdapter

            viewModel.getStudents().observe(viewLifecycleOwner) { students ->
                setUpScreen(students)
                studentAdapter.setList(students)

                studentAdapter.onClickListener = {
                    EditFragment.newInstance(it).show(parentFragmentManager, EditFragment.TAG)
                }

                studentAdapter.onStatusChangeListener = { student: Student, i: Int ->

                    student.status = resources.getStringArray(R.array.status)[i]
                    viewModel.editStudent(student)
                }

                binding.fabSend.setOnClickListener {

                    var report = "Отстутствуют:\n"
                    val absentStudents = students.filter {
                        it.status != resources.getStringArray(R.array.status)[0]
                    }

                    if (absentStudents.isEmpty()) {
                        report = "Все присутствуют"
                    } else {
                        for (student in absentStudents) {
                            report += "${student.surname} - ${student.status}\n"
                        }
                    }

                    try {
                        val sendIntent = Intent(Intent.ACTION_SEND)
                        sendIntent.putExtra(Intent.EXTRA_TEXT, report)
                        sendIntent.type = "text/plain"
                        startActivity(sendIntent)

                    } catch (e: ActivityNotFoundException) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Ошибка!")
                            .setMessage("Нет нужного интента")
                            .setNeutralButton("ОК") { dialogInterface: DialogInterface, _ ->
                                dialogInterface.cancel()
                            }.show()
                    }
                }

                binding.fabAdd.setOnClickListener {
                    AddFragment().show(parentFragmentManager, AddFragment.TAG)
                }

            }
        }
    }

    private fun setUpScreen(students: List<Student>?) {
        if(students?.isNotEmpty() == true){
            binding.rvStudent.visibility = View.VISIBLE
            binding.llNoStudent.visibility = View.INVISIBLE
        } else{
            binding.rvStudent.visibility = View.INVISIBLE
            binding.llNoStudent.visibility = View.VISIBLE
        }
    }


    private fun setupToolbar() {
        binding.toolbarList.apply {

            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_info -> {
                        findNavController().navigate(R.id.action_listFragment_to_infoFragment)
                    }
                }
                true
            }
        }
    }

    companion object {
        const val STUDENT_KEY = "KEY"
    }
}