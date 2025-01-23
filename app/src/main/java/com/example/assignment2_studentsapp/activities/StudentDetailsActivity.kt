package com.example.assignment2_studentsapp.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment2_studentsapp.R
import com.example.assignment2_studentsapp.models.StudentsDatabase

class StudentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val studentName: TextView = findViewById(R.id.studentName)
        val studentId: TextView = findViewById(R.id.studentId)
        val studentPhone: TextView = findViewById(R.id.studentPhone)
        val studentAddress: TextView = findViewById(R.id.studentAddress)

        val position = intent.getIntExtra("position", -1)
        if (position != -1) {
            val student = StudentsDatabase.students[position]
            studentName.text = student.name
            studentId.text = student.id
            studentPhone.text = student.phone
            studentAddress.text = student.address
        }
    }
}