package com.example.community_repair_hub.ViewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.Components.ImageUploadUtil
import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class ReportIssueViewModel : ViewModel() {
    // Form fields
    var category by mutableStateOf("")
    var city by mutableStateOf("")
    var specificAddress by mutableStateOf("")
    var description by mutableStateOf("")
    var date by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)

    // UI state
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    private val apiService: ApiService = RetrofitClient.instance

    fun submitReport(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (category.isEmpty() || city.isEmpty()) {
            errorMessage = "Please enter the category and the city where the issue is located"
            onError(errorMessage!!)
            return
        }

        if (imageUri == null) {
            errorMessage = "Please upload an image of the issue"
            onError(errorMessage!!)
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                // Create multipart request
                val imagePart = ImageUploadUtil.uriToMultipart(context, imageUri!!, "image")
                val categoryPart = category.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityPart = city.toRequestBody("text/plain".toMediaTypeOrNull())
                val specificAddressPart = specificAddress.toRequestBody("text/plain".toMediaTypeOrNull())
                val descriptionPart = description.toRequestBody("text/plain".toMediaTypeOrNull())
                val datePart = date.toRequestBody("text/plain".toMediaTypeOrNull())

                Log.d("ReportIssue", "Submitting report with image: $imageUri")
                Log.d("ReportIssue", "Category: $category, City: $city, SpecificAddress: $specificAddress, Description: $description, Date: $date")

                // Make API call
                val response = apiService.reportIssue(
                    image = imagePart,
                    category = categoryPart,
                    city = cityPart,
                    specificAddress = specificAddressPart,
                    description = descriptionPart,
                    issueDate = datePart
                )

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("ReportIssue", "Success response: $responseBody")
                    successMessage = "Your issue has been successfully submitted!"
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ReportIssue", "Error response: $errorBody")
                    errorMessage = "Failed to submit report: $errorBody"
                    onError(errorMessage ?: "Unknown error")
                }
            } catch (e: Exception) {
                Log.e("ReportIssue", "Error submitting report", e)
                errorMessage = "Failed to submit report: ${e.message}"
                onError(errorMessage ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }
    }

    fun resetForm() {
        category = ""
        city = ""
        specificAddress = ""
        description = ""
        date = ""
        imageUri = null
        errorMessage = null
        successMessage = null
    }
}