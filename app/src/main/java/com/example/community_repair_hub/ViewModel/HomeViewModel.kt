package com.example.community_repair_hub.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _issues = MutableStateFlow<List<IssueResponse>>(emptyList())
    val issues: StateFlow<List<IssueResponse>> = _issues

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchIssues() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getIssues()
                if (response.isSuccessful) {
                    _issues.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Failed: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
