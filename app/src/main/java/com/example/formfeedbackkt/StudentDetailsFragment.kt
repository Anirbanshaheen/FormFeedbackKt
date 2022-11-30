package com.example.formfeedbackkt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.formfeedbackkt.data.Student
import com.example.formfeedbackkt.databinding.FragmentStudentDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class StudentDetailsFragment : Fragment() {
    private val navigationArgs: StudentDetailsFragmentArgs by navArgs()
    lateinit var student: Student

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    private var _binding: FragmentStudentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(student: Student) {
        binding.apply {
            studentName.text = student.studentName
            studentMobile.text = student.studentMobile.toString()
            studentAddress.text = student.studentAddress
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    private fun editItem() {
        val action = StudentDetailsFragmentDirections.actionStudentDetailsFragmentToAddStudentFragment("Edit Item",student.id)
        this.findNavController().navigate(action)
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.deleteItem(student)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.studentId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            student = selectedItem
            bind(student)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}