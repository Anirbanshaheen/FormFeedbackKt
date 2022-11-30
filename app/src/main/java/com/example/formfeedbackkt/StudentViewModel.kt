package com.example.formfeedbackkt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.formfeedbackkt.data.Student
import com.example.formfeedbackkt.data.StudentDao
import kotlinx.coroutines.launch

class StudentViewModel(private val studentDao: StudentDao) : ViewModel() {

    val allStudents: LiveData<List<Student>> = studentDao.getItems().asLiveData()

    fun updateStudent(
        studentId: Int,
        studentName: String,
        studentMobile: Int,
        studentAddress: String
    ) {
        val updatedStudent = getUpdatedStudentEntry(studentId, studentName, studentMobile, studentAddress)
        updateStudent(updatedStudent)
    }

    private fun updateStudent(student: Student) {
        viewModelScope.launch {
            studentDao.update(student)
        }
    }

    fun addNewStudent(studentName: String, studentMobile: Int, studentAddress: String) {
        val newStudent = getNewStudentEntry(studentName, studentMobile, studentAddress)
        insertStudent(newStudent)
    }

    private fun insertStudent(student: Student) {
        viewModelScope.launch {
            studentDao.insert(student)
        }
    }

    fun deleteItem(student: Student) {
        viewModelScope.launch {
            studentDao.delete(student)
        }
    }

    fun retrieveItem(id: Int): LiveData<Student> {
        return studentDao.getItem(id).asLiveData()
    }

    fun isEntryValid(studentName: String, studentMobile: Int, studentAddress: String): Boolean {
        if (studentName.isBlank() || studentMobile == 0 || studentAddress.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewStudentEntry(studentName: String, studentMobile: Int, studentAddress: String): Student {
        return Student(
            studentName = studentName,
            studentMobile = studentMobile,
            studentAddress = studentAddress
        )
    }

    private fun getUpdatedStudentEntry(
        studentId: Int,
        studentName: String,
        studentMobile: Int,
        studentAddress: String
    ): Student {
        return Student(
            id = studentId,
            studentName = studentName,
            studentMobile = studentMobile,
            studentAddress = studentAddress
        )
    }
}

class StudentViewModelFactory(private val studentDao: StudentDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(studentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

