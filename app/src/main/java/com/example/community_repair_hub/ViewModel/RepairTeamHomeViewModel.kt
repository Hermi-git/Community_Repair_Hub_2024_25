package com.example.community_repair_hub.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.data.network.repository.IssueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepairTeamHomeViewModel : ViewModel() {
    private val issueRepository = IssueRepository()

    private val _issues = MutableStateFlow<List<IssueResponse>>(emptyList())
    val issues: StateFlow<List<IssueResponse>> = _issues.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchType = MutableStateFlow<SearchType>(SearchType.NONE)
    val searchType: StateFlow<SearchType> = _searchType.asStateFlow()

    private val _selectedStatus = MutableStateFlow<String?>(null)
    val selectedStatus: StateFlow<String?> = _selectedStatus.asStateFlow()

    // Status options for filtering
    val statusOptions = listOf("All", "Pending", "In Progress", "Completed")

    init {
        fetchIssues()
    }

    fun fetchIssues() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                issueRepository.getIssues().fold(
                    onSuccess = { issues ->
                        _issues.value = issues
                    },
                    onFailure = { exception ->
                        _error.value = "Failed to fetch issues: ${exception.message}"
                    }
                )
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByStatus(status: String?) {
        _selectedStatus.value = if (status == "All") null else status
        performSearch()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchType(type: SearchType) {
        _searchType.value = type
        if (_searchQuery.value.isEmpty()) {
            fetchIssues()
        }
    }

    fun performSearch() {
        if (_searchQuery.value.isEmpty()) {
            fetchIssues()
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val result = when (_searchType.value) {
                    SearchType.CATEGORY -> issueRepository.searchByCategory(_searchQuery.value)
                    SearchType.LOCATION -> issueRepository.searchByLocation(_searchQuery.value)
                    else -> issueRepository.getIssues()
                }

                result.fold(
                    onSuccess = { issues ->
                        var filteredIssues = issues
                        // Apply additional status filter if selected
                        _selectedStatus.value?.let { status ->
                            filteredIssues = filteredIssues.filter { it.status == status }
                        }
                        _issues.value = filteredIssues
                    },
                    onFailure = { exception ->
                        _error.value = "Search failed: ${exception.message}"
                    }
                )
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

enum class SearchType {
    NONE, CATEGORY, LOCATION
}
