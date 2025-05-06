package com.example.community_repair_hub.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
//import java.io.InputStream
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.RequestBody.Companion.toRequestBody


class ReportIssueViewModel : ViewModel() {
    // Form fields
    var category by mutableStateOf("")
    var city by mutableStateOf("")
    var specificAddress by mutableStateOf("")
    var description by mutableStateOf("")
    var date by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)
    var imageUrl by mutableStateOf<String?>(null)

    // UI state
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    private val apiService: ApiService = RetrofitClient.instance

    fun uploadImage(
        uri: Uri,
        context: Context,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val inputStream = context.contentResolver.openInputStream(uri)
                    ?: throw Exception("Could not open file stream")

                inputStream.use { stream ->
                    // Create a temporary file
                    val file = File.createTempFile(
                        "upload_",
                        ".jpg",
                        context.cacheDir
                    ).apply {
                        deleteOnExit()
                    }

                    FileOutputStream(file).use { outputStream ->
                        stream.copyTo(outputStream)
                    }

                    // Create multipart request body
                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        requestFile
                    )

                    // Upload the image
                    val response = apiService.uploadImage(body)

                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            try {
                                val imageUrl = responseBody.string()
                                if (imageUrl.isNotEmpty()) {
                                    this@ReportIssueViewModel.imageUrl = imageUrl
                                    onSuccess(imageUrl)
                                    successMessage = "Image uploaded successfully"
                                } else {
                                    throw Exception("Empty image URL in response")
                                }
                            } catch (e: Exception) {
                                throw Exception("Failed to parse response: ${e.message}")
                            }
                        } ?: throw Exception("Empty response body")
                    } else {
                        val errorMsg = try {
                            "Server error: ${response.errorBody()?.string() ?: response.message()}"
                        } catch (e: Exception) {
                            "Server error: ${response.message()}"
                        }
                        throw Exception(errorMsg)
                    }
                }
            } catch (e: Exception) {
                val errorMsg = "Error uploading image: ${e.message ?: "Unknown error"}"
                onError(errorMsg)
                errorMessage = errorMsg
                Log.e("ReportIssueViewModel", errorMsg, e)
            } finally {
                isLoading = false
            }
        }
    }

    fun submitReport(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Validate all required fields
        if (category.isEmpty() || city.isEmpty()) {
            errorMessage = "Please enter the category and the city where the issue is located"
            onError(errorMessage!!)
            return
        }

        if (imageUrl == null && imageUri == null) {
            errorMessage = "Please upload an image of the issue"
            onError(errorMessage!!)
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                if (imageUri != null && imageUrl == null) {
                    uploadImage(
                        uri = imageUri!!,
                        context = context,
                        onSuccess = { url ->
                            submitReportData(url, onSuccess, onError)
                        },
                        onError = { error ->
                            isLoading = false
                            errorMessage = error
                            onError(error)
                        }
                    )
                } else {
                    submitReportData(imageUrl ?: "", onSuccess, onError)
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Failed to submit report: ${e.message}"
                onError(errorMessage ?: "Unknown error")
            }
        }
    }

    private fun submitReportData(
        imageUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val reportData = mapOf(
                    "imageURL" to imageUrl,
                    "category" to category,
                    "city" to city,
                    "specificAdress" to specificAddress,
                    "description" to description,
                    "Date" to date
                )
                val json = Gson().toJson(reportData)
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


                // TODO: Uncomment when API is ready
                // val response = apiService.reportIssue(requestBody)
                // if (!response.isSuccessful) {
                //     throw Exception(response.errorBody()?.string() ?: "Unknown error")
                // }

                successMessage = "Your issue has been successfully submitted!"
                onSuccess()
            } catch (e: Exception) {
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
        imageUrl = null
        errorMessage = null
        successMessage = null
    }
}