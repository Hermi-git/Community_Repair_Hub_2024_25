package com.example.community_repair_hub.ViewModel

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

    fun fetchIssues() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            issueRepository.getIssues()
                .onSuccess { issues ->
                    _issues.value = issues
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isLoading.value = false
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