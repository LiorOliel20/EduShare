package com.example.edushare.model

data class User(
    val uid: String? = null, // Firebase User ID
    val displayName: String? = null,
    val profileImageUrl: String? = null
)