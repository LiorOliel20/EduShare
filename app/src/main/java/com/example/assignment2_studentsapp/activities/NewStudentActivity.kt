package com.example.assignment2_studentsapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment2_studentsapp.R
import com.example.assignment2_studentsapp.models.Student
import com.example.assignment2_studentsapp.models.StudentsDatabase

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        val studentNameInput: EditText = findViewById(R.id.studentNameInput)
        val studentIdInput: EditText = findViewById(R.id.studentIdInput)
        val studentPhoneInput: EditText = findViewById(R.id.studentPhoneInput)
        val studentAddressInput: EditText = findViewById(R.id.studentAddressInput)
        val saveStudentButton: Button = findViewById(R.id.saveStudentButton)

        saveStudentButton.setOnClickListener {
            val name = studentNameInput.text.toString()
            val id = studentIdInput.text.toString()
            val phone = studentPhoneInput.text.toString()
            val address = studentAddressInput.text.toString()

            if (name.isNotBlank() && id.isNotBlank() && phone.isNotBlank() && address.isNotBlank()) {
                StudentsDatabase.addStudent(
                    Student(
                        id = id,
                        name = name,
                        picResId = R.drawable.student_placeholder,
                        phone = phone,
                        address = address
                    )
                )
                Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}