package com.example.edushare.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edushare.model.AuthResult
import com.example.edushare.model.Resource
import com.example.edushare.model.StudyPlan
import com.example.edushare.model.StudyPlanRepository
import com.example.edushare.model.User
import kotlinx.coroutines.launch

class StudyPlanViewModel : ViewModel() {

    private val repository = StudyPlanRepository()

    private val _studyPlans = MutableLiveData<List<StudyPlan>>()
    val studyPlans: LiveData<List<StudyPlan>> = _studyPlans

    private val _userStudyPlans = MutableLiveData<List<StudyPlan>>()
    val userStudyPlans: LiveData<List<StudyPlan>> = _userStudyPlans

    private val _addStudyPlanResult = MutableLiveData<Resource<String>>()
    val addStudyPlanResult: LiveData<Resource<String>> = _addStudyPlanResult

    private val _updateStudyPlanResult = MutableLiveData<Resource<String>>()
    val updateStudyPlanResult: LiveData<Resource<String>> = _updateStudyPlanResult

    private val _deleteStudyPlanResult = MutableLiveData<Resource<String>>()
    val deleteStudyPlanResult: LiveData<Resource<String>> = _deleteStudyPlanResult

    // Authentication LiveData
    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    // User profile LiveData
    private val _userProfile = MutableLiveData<Resource<User>>()
    val userProfile: LiveData<Resource<User>> = _userProfile

    // Authentication methods
    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = repository.registerUser(email, password)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = repository.loginUser(email, password)
        }
    }

    fun logoutUser() {
        repository.logoutUser()
    }

    fun getCurrentUser() = repository.getCurrentUser()

    // Study Plan methods
    fun addStudyPlan(studyPlan: StudyPlan) {
        viewModelScope.launch {
            _addStudyPlanResult.value = Resource.Loading
            _addStudyPlanResult.value = repository.addStudyPlan(studyPlan)
        }
    }

    fun getAllStudyPlans() {
        viewModelScope.launch {
            when (val result = repository.getAllStudyPlans()) {
                is Resource.Success -> _studyPlans.value = result.data
                is Resource.Error -> {
                    // Handle error, show message to user
                }
                is Resource.Loading -> {
                    // Handle loading state
                }
            }
        }
    }

    fun getStudyPlansByUser(userId: String) {
        viewModelScope.launch {
            when (val result = repository.getStudyPlansByUser(userId)) {
                is Resource.Success -> _userStudyPlans.value = result.data
                is Resource.Error -> {
                    // Handle error
                }
                is Resource.Loading -> {
                    // Handle loading state
                }
            }
        }
    }

    fun updateStudyPlan(studyPlan: StudyPlan) {
        viewModelScope.launch {
            _updateStudyPlanResult.value = Resource.Loading
            _updateStudyPlanResult.value = repository.updateStudyPlan(studyPlan)
        }
    }

    fun deleteStudyPlan(studyPlanId: String) {
        viewModelScope.launch {
            _deleteStudyPlanResult.value = Resource.Loading
            _deleteStudyPlanResult.value = repository.deleteStudyPlan(studyPlanId)
        }
    }

    // User profile methods
    fun updateUserProfile(user: User) {
        viewModelScope.launch {
            //_updateUserProfileResult.value = Resource.Loading
            //_updateUserProfileResult.value = repository.updateUserProfile(user)
        }
    }

    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            _userProfile.value = Resource.Loading
            _userProfile.value = repository.getUserProfile(userId)
        }
    }
}