package com.example.assignment2_studentsapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2_studentsapp.R
import com.example.assignment2_studentsapp.activities.StudentDetailsActivity
import com.example.assignment2_studentsapp.models.Student

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onCheckChanged: (Student, Boolean) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.studentName)
        private val studentId: TextView = itemView.findViewById(R.id.studentId)
        private val studentPhone: TextView = itemView.findViewById(R.id.studentPhone)
        private val studentImage: ImageView = itemView.findViewById(R.id.studentImage)
        private val checkBox: CheckBox = itemView.findViewById(R.id.studentCheckBox)

        fun bind(student: Student) {
            studentName.text = student.name
            studentId.text = student.id
            studentPhone.text = student.phone
            studentImage.setImageResource(student.picResId) // מציג את התמונה
            checkBox.isChecked = student.isChecked

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(student, isChecked)
            }

            itemView.setOnClickListener {
                // כאן אנחנו מפנים למסך פרטי הסטודנט
                val intent = Intent(itemView.context, StudentDetailsActivity::class.java)
                intent.putExtra("STUDENT_ID", student.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}