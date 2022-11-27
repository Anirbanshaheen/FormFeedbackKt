package com.example.formfeedbackkt.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("SELECT * from student ORDER BY studentName ASC")
    fun getItems(): Flow<List<Student>>

    @Query("SELECT * from student WHERE id = :id")
    fun getItem(id: Int): Flow<Student>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)
}