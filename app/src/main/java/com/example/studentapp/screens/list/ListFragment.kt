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
import com.example.studentapp.R
import com.example.studentapp.adapter.StudentAdapter
import com.example.studentapp.databinding.FragmentListBinding
import com.example.studentapp.domain.Student

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
        viewModel.initDb()
        studentAdapter = StudentAdapter(requireContext())
        setupToolbar()

        with(binding.rvStudent) {

            this.adapter = studentAdapter

            viewModel.getStudents().observe(viewLifecycleOwner) { students ->

                studentAdapter.setList(students)

                studentAdapter.onClickListener = {

                    val bundle = Bundle()
                    bundle.putSerializable(STUDENT_KEY, it)

                    findNavController().navigate(R.id.action_listFragment_to_editFragment, bundle)
                }

                studentAdapter.onStatusChangeListener = { student: Student, i: Int ->

                    student.status = i
                    viewModel.editStudent(student)
                }

                binding.fabSend.setOnClickListener {

                    var report = "Отстутствуют:\n"
                    val absentStudents = students.filter { it.status != 0 }

                    if (absentStudents.isEmpty()) {
                        report = "Все присутствуют"
                    } else {
                        for (student in absentStudents) {
                            val status = resources.getStringArray(R.array.status)[student.status]
                            report += "${student.surname} - $status\n"
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
            }
        }
    }

    private fun setupToolbar() {

        binding.toolbarList.apply {

            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_add -> {
                        findNavController().navigate(R.id.action_listFragment_to_addFragment)
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