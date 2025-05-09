package com.example.community_repair_hub.data.network.repository

import android.util.Log
import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.*
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "AuthRepository"

object AuthRepository {
    private val apiService = RetrofitClient.instance

    sealed class AuthResult<out T> {
        data class Success<T>(val data: T) : AuthResult<T>()
        data class Error(val exception: Exception) : AuthResult<Nothing>()
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
        city: String
    ): AuthResult<SignupResponse> {
        return try {
            Log.d(TAG, "Attempting signup for email: $email")
            val response = apiService.signup(
                SignupRequest(name, email, password, role, region, city)
            )
            Log.d(TAG, "Signup response code: ${response.code()}")
            Log.d(TAG, "Signup response body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d(TAG, "Signup successful: ${it.success}")
                    AuthResult.Success(it)
                } ?: AuthResult.Error(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Signup failed: $errorBody")
                AuthResult.Error(Exception(errorBody ?: "Signup failed: ${response.code()}"))
            }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error during signup", e)
            AuthResult.Error(Exception("Server error: ${e.code()}"))
        } catch (e: IOException) {
            Log.e(TAG, "Network error during signup", e)
            AuthResult.Error(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during signup", e)
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