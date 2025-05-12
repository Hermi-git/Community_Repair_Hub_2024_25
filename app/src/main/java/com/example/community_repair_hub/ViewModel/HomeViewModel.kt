package com.example.community_repair_hub.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.data.network.repository.AuthRepository
import com.example.community_repair_hub.data.network.repository.IssueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val issueRepository: IssueRepository = IssueRepository(),
    private val authRepository: AuthRepository = AuthRepository
) : ViewModel() {

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

    private val _issues = MutableStateFlow<List<IssueResponse>>(emptyList())
    val issues: StateFlow<List<IssueResponse>> = _issues

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchIssues()
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            when (val result = authRepository.getProfile()) {
                is AuthRepository.AuthResult.Success -> {
                    _profileImageUrl.value = result.data.imageUrl
                    _profileImageUrl.value = result.data.imageUrl
                    Log.d("HomeViewModel", "Fetched profile imageUrl: ${result.data.imageUrl}")
                }
                is AuthRepository.AuthResult.Error -> {
                    Log.e("HomeViewModel", "Error fetching user profile", result.exception)
                }
            }
        }
    }

    fun fetchIssues() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                issueRepository.getIssues().fold(
                    onSuccess = { issuesList ->
                        _issues.value = issuesList
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to fetch issues"
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchByCategory(category: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            issueRepository.searchByCategory(category)
                .onSuccess { issues ->
                    _issues.value = issues
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isLoading.value = false
        }
    }

    fun searchByLocation(location: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            issueRepository.searchByLocation(location)
                .onSuccess { issues ->
                    _issues.value = issues
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isLoading.value = false
        }
    }
}