package com.example.formfeedbackkt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formfeedbackkt.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StudentListAdapter {
            val action =
                StudentListFragmentDirections.actionStudentListFragmentToAddStudentFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allStudents.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = StudentListFragmentDirections.actionStudentListFragmentToAddStudentFragment("Add Item")
            this.findNavController().navigate(action)
        }
    }
}