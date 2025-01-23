package com.example.assignment2_studentsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2_studentsapp.R
import com.example.assignment2_studentsapp.adapters.StudentAdapter
import com.example.assignment2_studentsapp.models.Student
import com.example.assignment2_studentsapp.models.StudentsDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentsListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddStudent: FloatingActionButton
    private val students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)

        recyclerView = findViewById(R.id.recyclerViewStudents)
        fabAddStudent = findViewById(R.id.fabAddStudent)

        val adapter = StudentAdapter(students) { student, isChecked ->
            student.isChecked = isChecked
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fabAddStudent.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
        loadDummyData(adapter)
    }

    private fun loadDummyData(adapter: StudentAdapter) {
        StudentsDatabase.addStudent(
            Student(
                id = "123456",
                name = "John Doe",
                picResId = R.drawable.student_placeholder, // תמונה סטטית שנמצאת ב-drawable
                phone = "052341241",
                address = "Address Here"
            )
        )

        StudentsDatabase.addStudent(
            Student(
                id = "234567",
                name = "Jane Smith",
                picResId = R.drawable.student_placeholder,
                phone = "052345678",
                address = "Another Address"
            )
        )

        adapter.notifyDataSetChanged()
    }
}
