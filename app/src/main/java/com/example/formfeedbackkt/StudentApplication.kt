package com.example.formfeedbackkt

import android.app.Application
import com.example.formfeedbackkt.data.StudentRoomDatabase


class StudentApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: StudentRoomDatabase by lazy { StudentRoomDatabase.getDatabase(this) }
}
