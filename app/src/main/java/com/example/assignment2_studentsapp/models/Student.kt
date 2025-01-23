package com.example.assignment2_studentsapp.models

import com.example.assignment2_studentsapp.R

data class Student(
    var name: String,
    var id: String,
    var phone: String,
    var address: String,
    var isChecked: Boolean = false,
    var picResId: Int = R.drawable.student_placeholder,
)

object StudentsDatabase {
    val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(index: Int, updatedStudent: Student) {
        students[index] = updatedStudent
    }

    fun deleteStudent(index: Int) {
        students.removeAt(index)
    }

    init {
        students.addAll(
            listOf(
                Student(
                    id = "123456",
                    name = "John Doe",
                    picResId = R.drawable.student_placeholder,
                    phone = "052341241",
                    address = "Address Here"
                ),
                Student(
                    id = "234567",
                    name = "Jane Smith",
                    picResId = R.drawable.student_placeholder,
                    phone = "052345678",
                    address = "Another Address"
                ),
                Student(
                    id = "345678",
                    name = "Samuel Green",
                    picResId = R.drawable.student_placeholder,
                    phone = "052389765",
                    address = "Somewhere else"
                )
            )
        )
    }
}
