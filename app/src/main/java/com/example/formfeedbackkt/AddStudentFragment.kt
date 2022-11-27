package com.example.formfeedbackkt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.formfeedbackkt.data.Student
import com.example.formfeedbackkt.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    private val navigationArgs: StudentDetailsFragmentArgs by navArgs()
    lateinit var student: Student

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.studentName.text.toString(),
            binding.studentMobile.text.toString().toInt(),
            binding.studentAddress.text.toString()
        )
    }

    private fun bind(student: Student) {
        binding.apply {
            studentName.setText(student.studentName, TextView.BufferType.SPANNABLE)
            studentMobile.setText(student.studentMobile, TextView.BufferType.SPANNABLE)
            studentAddress.setText(student.studentAddress, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewStudent(
                binding.studentName.text.toString(),
                binding.studentMobile.text.toString().toInt(),
                binding.studentAddress.text.toString(),
            )
            val action = AddStudentFragmentDirections.actionAddStudentFragmentToStudentListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateStudent(
                this.navigationArgs.studentId,
                this.binding.studentName.text.toString(),
                this.binding.studentMobile.text.toString().toInt(),
                this.binding.studentAddress.text.toString()
            )
            val action = AddStudentFragmentDirections.actionAddStudentFragmentToStudentListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.studentId
        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                student = selectedItem
                bind(student)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewItem()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}