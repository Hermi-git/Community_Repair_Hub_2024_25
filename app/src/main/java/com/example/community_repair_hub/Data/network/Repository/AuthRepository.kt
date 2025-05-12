package com.example.community_repair_hub.data.network.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "AuthRepository"

object AuthRepository {
    private val apiService = RetrofitClient.instance

    sealed class AuthResult<out T> {
        data class Success<T>(val data: T) : AuthResult<T>()
        data class Error(val exception: Exception) : AuthResult<Nothing>()
    }
    suspend fun getProfile(): AuthResult<UserData> {
        return try {
            val response = apiService.getProfile() // Implement this in your ApiService
            if (response.isSuccessful) {
                response.body()?.let { AuthResult.Success(it) }
                    ?: AuthResult.Error(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                AuthResult.Error(Exception(errorBody ?: "Profile fetch failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            AuthResult.Error(Exception("Profile fetch error: ${e.message}"))
        }
    }

    suspend fun login(email: String, password: String): AuthResult<LoginResponse> {
        return try {
            Log.d(TAG, "Attempting login for email: $email")
            val response = apiService.login(LoginRequest(email, password))
            Log.d(TAG, "Login response code: ${response.code()}")
            Log.d(TAG, "Login response body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d(TAG, "Login successful: ${it.success}")
                    AuthResult.Success(it)
                } ?: AuthResult.Error(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Login failed: $errorBody")
                AuthResult.Error(Exception(errorBody ?: "Login failed: ${response.code()}"))
            }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error during login", e)
            AuthResult.Error(Exception("Server error: ${e.code()}"))
        } catch (e: IOException) {
            Log.e(TAG, "Network error during login", e)
            AuthResult.Error(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during login", e)
            AuthResult.Error(Exception("Unexpected error: ${e.message}"))
        }
    }

    suspend fun signup(
        name: String,
        email: String,
        password: String,
        role: String,
        region: String,
        city: String,
        imageUri: Uri?,
        context: Context
    ): AuthResult<SignupResponse> {
        return try {
            val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val emailPart = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordPart = password.toRequestBody("text/plain".toMediaTypeOrNull())
            val rolePart = role.toRequestBody("text/plain".toMediaTypeOrNull())
            val regionPart = region.toRequestBody("text/plain".toMediaTypeOrNull())
            val cityPart = city.toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = imageUri?.let {
                val inputStream = context.contentResolver.openInputStream(it)
                val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
                FileOutputStream(file).use { output -> inputStream?.copyTo(output) }
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            val response = apiService.signup(
                namePart, emailPart, passwordPart, rolePart, regionPart, cityPart, imagePart
            )
            if (response.isSuccessful) {
                response.body()?.let { AuthResult.Success(it) }
                    ?: AuthResult.Error(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                AuthResult.Error(Exception(errorBody ?: "Signup failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            AuthResult.Error(Exception("Unexpected error: ${e.message}"))
        }
    }

    suspend fun logout(): AuthResult<LogoutResponse> {
        return try {
            val response = apiService.logout()
            if (response.isSuccessful) {
                response.body()?.let {
                    AuthResult.Success(it)
                } ?: AuthResult.Error(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                AuthResult.Error(Exception(errorBody ?: "Logout failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            AuthResult.Error(Exception("Logout error: ${e.message}"))
        }
    }

    suspend fun resetPassword(token: String, newPassword: String): AuthResult<ResetPasswordResponse> {
        return try {
            val response = apiService.resetPassword(token, ResetPasswordRequest(newPassword))
            if (response.isSuccessful) {
                response.body()?.let {
                    AuthResult.Success(it)
                } ?: AuthResult.Error(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                AuthResult.Error(Exception(errorBody ?: "Reset password failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            AuthResult.Error(Exception("Reset password error: ${e.message}"))
        }
    }
}