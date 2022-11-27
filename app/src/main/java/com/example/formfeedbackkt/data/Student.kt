package com.example.formfeedbackkt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "studentName")
    val studentName: String,
    @ColumnInfo(name = "studentMobile")
    val studentMobile: Int,
    @ColumnInfo(name = "studentAddress")
    val studentAddress: String
)