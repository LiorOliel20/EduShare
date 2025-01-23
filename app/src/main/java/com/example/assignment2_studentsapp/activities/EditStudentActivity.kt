package com.example.assignment2_studentsapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment2_studentsapp.R
import com.example.assignment2_studentsapp.models.Student
import com.example.assignment2_studentsapp.models.StudentsDatabase

class EditStudentActivity : AppCompatActivity() {

    private lateinit var editStudentName: EditText
    private lateinit var editStudentId: EditText
    private lateinit var editStudentPhone: EditText
    private lateinit var editStudentAddress: EditText
    private lateinit var editStudentImage: ImageView
    private lateinit var buttonSaveChanges: Button
    private lateinit var buttonDeleteStudent: Button
    private lateinit var buttonChangeImage: Button
    private var studentIndex: Int = -1
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Initialize views
        editStudentName = findViewById(R.id.editStudentName)
        editStudentId = findViewById(R.id.editStudentId)
        editStudentPhone = findViewById(R.id.editStudentPhone)
        editStudentAddress = findViewById(R.id.editStudentAddress)
        editStudentImage = findViewById(R.id.editStudentImage)
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges)
        buttonDeleteStudent = findViewById(R.id.buttonDeleteStudent)
        buttonChangeImage = findViewById(R.id.editStudentImage)

        // Get student index and current student data from intent
        studentIndex = intent.getIntExtra("studentIndex", -1)
        val student = StudentsDatabase.students[studentIndex]

        // Populate fields with current student data
        editStudentName.setText(student.name)
        editStudentId.setText(student.id)
        editStudentPhone.setText(student.phone)
        editStudentAddress.setText(student.address)
        editStudentImage.setImageResource(R.drawable.student_placeholder)

        // Set up button listeners
        buttonChangeImage.setOnClickListener {
            // Launch intent to pick an image from the gallery
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }

        buttonSaveChanges.setOnClickListener {
            val updatedName = editStudentName.text.toString()
            val updatedId = editStudentId.text.toString()
            val updatedPhone = editStudentPhone.text.toString()
            val updatedAddress = editStudentAddress.text.toString()

            if (updatedName.isNotEmpty() && updatedId.isNotEmpty() && updatedPhone.isNotEmpty() && updatedAddress.isNotEmpty()) {
                // Update student in the database
                val updatedStudent = Student(updatedId, updatedName, updatedPhone, updatedAddress, student.isChecked)
                StudentsDatabase.updateStudent(studentIndex, updatedStudent)
                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDeleteStudent.setOnClickListener {
            StudentsDatabase.deleteStudent(studentIndex)
            Toast.makeText(this, "Student deleted successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Handle the result from the gallery intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            // Set the selected image in ImageView
            editStudentImage.setImageURI(selectedImageUri)
        }
    }
}