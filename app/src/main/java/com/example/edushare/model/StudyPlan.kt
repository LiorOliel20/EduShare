package com.example.edushare.model

data class StudyPlan(
    val id: String? = null, // Firebase ID
    val title: String = "",
    val description: String = "",
    val imageUrl: String? = null,
    val fileUrl: String? = null,
    val category: String = "",
    val userId: String? = null // Firebase User ID
)