package com.example.community_repair_hub.data.network.repository

import android.util.Log
import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.IssueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IssueRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getIssues(): Result<List<IssueResponse>> = withContext(Dispatchers.IO) {
        try {
            Log.d("IssueRepository", "Fetching issues...")
            val response = apiService.getIssues()
            Log.d("IssueRepository", "Response received: success=${response.success}, message=${response.message}")
            Log.d("IssueRepository", "Data size: ${response.data.size}")
            
            if (response.success) {
                Result.success(response.data)
            } else {
                Log.e("IssueRepository", "API returned error: ${response.message}")
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Log.e("IssueRepository", "Error fetching issues", e)
            Result.failure(e)
        }
    }

    suspend fun searchByCategory(category: String): Result<List<IssueResponse>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchByCategory(category)
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchByLocation(location: String): Result<List<IssueResponse>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchByLocation(location)
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun reportIssue(issue: Map<String, String>): Result<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.reportIssue(issue)
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}