package com.example.community_repair_hub.ViewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.community_repair_hub.data.network.model.IssueResponse
//import com.example.community_repair_hub.data.network.RetrofitClient
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class RepairTeamHomeViewModel : ViewModel() {
//
//    private val _issues = MutableStateFlow<List<IssueResponse>>(emptyList())
//    val issues: StateFlow<List<IssueResponse>> = _issues.asStateFlow()
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error.asStateFlow()
//
//    private val _searchQuery = MutableStateFlow("")
//    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
//
//    private val _searchType = MutableStateFlow<SearchType>(SearchType.NONE)
//    val searchType: StateFlow<SearchType> = _searchType.asStateFlow()
//
//    private val _selectedStatus = MutableStateFlow<String?>(null)
//    val selectedStatus: StateFlow<String?> = _selectedStatus.asStateFlow()
//
//    // Status options for filtering
//    val statusOptions = listOf("All", "Pending", "In Progress", "Completed")
//
//    init {
//        fetchIssues()
//    }
//
//    fun fetchIssues() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = RetrofitClient.instance.getIssues()
//                if (response.isSuccessful) {
//                    _issues.value = response.body() ?: emptyList()
//                } else {
//                    _error.value = "Failed: ${response.code()}"
//                }
//            } catch (e: Exception) {
//                _error.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun filterByStatus(status: String?) {
//        _selectedStatus.value = if (status == "All") null else status
//        performSearch()
//    }
//
//    fun setSearchQuery(query: String) {
//        _searchQuery.value = query
//    }
//
//    fun setSearchType(type: SearchType) {
//        _searchType.value = type
//        if (_searchQuery.value.isEmpty()) {
//            fetchIssues()
//        }
//    }
//    fun performSearch() {
//        if (_searchQuery.value.isEmpty()) {
//            fetchIssues()
//            return
//        }
//
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = when (_searchType.value) {
//                    SearchType.CATEGORY -> RetrofitClient.instance.searchIssuesByCategory(_searchQuery.value)
//                    SearchType.LOCATION -> RetrofitClient.instance.searchIssuesByLocation(_searchQuery.value)
//                    else -> RetrofitClient.instance.getIssues()
//                }
//
//                if (response.isSuccessful) {
//                    var filteredIssues = response.body() ?: emptyList()
//                    // Apply additional status filter if selected
//                    _selectedStatus.value?.let { status ->
//                        filteredIssues = filteredIssues.filter { it.Status == status }
//                    }
//                    _issues.value = filteredIssues
//                } else {
//                    _error.value = "Failed: ${response.code()}"
//                }
//            } catch (e: Exception) {
//                _error.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}
//
//enum class SearchType {
//    NONE, CATEGORY, LOCATION
//}
