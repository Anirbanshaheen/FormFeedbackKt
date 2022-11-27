package com.example.formfeedbackkt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.formfeedbackkt.data.Student
import com.example.formfeedbackkt.databinding.StudentListItemBinding

class StudentListAdapter(private val onItemClicked: (Student) -> Unit) :
    ListAdapter<Student, StudentListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            StudentListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: StudentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.studentName.text = student.studentName
            binding.studentMobile.text = student.studentMobile.toString()
            binding.studentAddress.text = student.studentAddress
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.studentName == newItem.studentName
            }
        }
    }
}
