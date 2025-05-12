package com.example.community_repair_hub.ViewModel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.data.network.model.UpdateIssueRequest
import com.example.community_repair_hub.data.network.repository.IssueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun RepairDetailScreen(modifier: Modifier = Modifier, navController: NavHostController) {

}

class IssueDetailViewModel(private val repository: IssueRepository) : ViewModel() {
    private val _issue = MutableStateFlow<IssueResponse?>(null)
    val issue: StateFlow<IssueResponse?> = _issue.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchIssue(issueId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.getIssueById(issueId)
            if (result.isSuccess) {
                _issue.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun updateIssueStatus(issueId: String, description: String, status: String, repairNotes: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val request = UpdateIssueRequest(issueId, description, status, repairNotes)
            val result = repository.updateIssueStatus(request)
            if (result.isSuccess) {
                _issue.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }
}