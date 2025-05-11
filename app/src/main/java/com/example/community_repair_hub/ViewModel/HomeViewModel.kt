package com.example.community_repair_hub.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.data.network.repository.IssueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val issueRepository: IssueRepository = IssueRepository()
) : ViewModel() {

    private val _issues = MutableStateFlow<List<IssueResponse>>(emptyList())
    val issues: StateFlow<List<IssueResponse>> = _issues

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchIssues()
    }

    fun fetchIssues() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                issueRepository.getIssues().fold(
                    onSuccess = { issuesList ->
                        Log.d("HomeViewModel", "Fetched ${issuesList.size} issues")
                        issuesList.forEach { issue ->
                            Log.d("HomeViewModel", "Issue: ${issue.category} - ${issue.description}")
                        }
                        _issues.value = issuesList
                    },
                    onFailure = { exception ->
                        Log.e("HomeViewModel", "Error fetching issues", exception)
                        _error.value = exception.message ?: "Failed to fetch issues"
                    }
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Exception in fetchIssues", e)
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