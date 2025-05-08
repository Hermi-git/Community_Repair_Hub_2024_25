package com.example.community_repair_hub.data.network.repository

import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.IssueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IssueRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getIssues(): Result<List<IssueResponse>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getIssues()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No issues found"))
            } else {
                Result.failure(Exception("Failed to fetch issues: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchByCategory(category: String): Result<List<IssueResponse>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchByCategory(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No issues found for category: $category"))
            } else {
                Result.failure(Exception("Failed to search by category: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchByLocation(location: String): Result<List<IssueResponse>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchByLocation(location)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No issues found for location: $location"))
            } else {
                Result.failure(Exception("Failed to search by location: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun reportIssue(issue: Map<String, String>): Result<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.reportIssue(issue)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to get response body"))
            } else {
                Result.failure(Exception("Failed to report issue: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}