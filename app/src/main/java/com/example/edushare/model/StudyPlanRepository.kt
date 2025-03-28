package com.example.edushare.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudyPlanRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val studyPlansCollection = firestore.collection("study_plans")

    // Authentication
    suspend fun registerUser(email: String, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(auth.currentUser)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    suspend fun loginUser(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(auth.currentUser)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser

    // Study Plans
    suspend fun addStudyPlan(studyPlan: StudyPlan): Resource<String> {
        return try {
            val documentReference = studyPlansCollection.document()
            val planWithId = studyPlan.copy(id = documentReference.id, userId = auth.currentUser?.uid)
            documentReference.set(planWithId).await()
            Resource.Success("Study plan added successfully with ID: ${documentReference.id}")
        } catch (e: Exception) {
            Resource.Error("Failed to add study plan: ${e.message}")
        }
    }

    suspend fun getAllStudyPlans(): Resource<List<StudyPlan>> {
        return try {
            val snapshot = studyPlansCollection.get().await()
            val studyPlans = snapshot.documents.mapNotNull { document ->
                document.toObject(StudyPlan::class.java)
            }
            Resource.Success(studyPlans)
        } catch (e: Exception) {
            Resource.Error("Failed to get study plans: ${e.message}")
        }
    }

    suspend fun getStudyPlansByUser(userId: String): Resource<List<StudyPlan>> {
        return try {
            val snapshot = studyPlansCollection.whereEqualTo("userId", userId).get().await()
            val studyPlans = snapshot.documents.mapNotNull { document ->
                document.toObject(StudyPlan::class.java)
            }
            Resource.Success(studyPlans)
        } catch (e: Exception) {
            Resource.Error("Failed to get study plans: ${e.message}")
        }
    }

    suspend fun updateStudyPlan(studyPlan: StudyPlan): Resource<String> {
        return try {
            studyPlansCollection.document(studyPlan.id ?: "").set(studyPlan).await()
            Resource.Success("Study plan updated successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to update study plan: ${e.message}")
        }
    }

    suspend fun deleteStudyPlan(studyPlanId: String): Resource<String> {
        return try {
            studyPlansCollection.document(studyPlanId).delete().await()
            Resource.Success("Study plan deleted successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to delete study plan: ${e.message}")
        }
    }

    // User profile
    suspend fun updateUserProfile(user: User): Resource<String> {
        return try {
            val userCollection = firestore.collection("users")
            val documentReference = userCollection.document(user.uid ?: "")
            documentReference.set(user).await()
            Resource.Success("User profile updated successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to update user profile: ${e.message}")
        }
    }

    suspend fun getUserProfile(userId: String): Resource<User> {
        return try {
            val userCollection = firestore.collection("users")
            val document = userCollection.document(userId).get().await()
            val user = document.toObject(User::class.java)
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("User profile not found")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to get user profile: ${e.message}")
        }
    }
}

// Helper classes for Authentication and Resource handling
sealed class AuthResult {
    data class Success(val user: com.google.firebase.auth.FirebaseUser?) : AuthResult()
    data class Error(val exception: Exception) : AuthResult()
}

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}