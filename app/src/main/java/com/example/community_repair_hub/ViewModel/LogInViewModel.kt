package com.example.community_repair_hub.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.LoginRequest
import com.example.community_repair_hub.Utills.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginInProgress: Boolean = false,
    val loginError: String? = null,
    val loginSuccess: Boolean = false,
    val authToken: String? = null
)

class LoginViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val apiService = RetrofitClient.instance

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, loginError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, loginError = null) }
    }

    fun login() {
        val currentState = _uiState.value

        // Validation
        when {
            currentState.email.isBlank() || currentState.password.isBlank() -> {
                _uiState.update { it.copy(loginError = "Email and password cannot be empty") }
                return
            }
            !currentState.email.contains("@") -> {
                _uiState.update { it.copy(loginError = "Invalid email format") }
                return
            }
            currentState.password.length < 6 -> {
                _uiState.update { it.copy(loginError = "Password must be at least 6 characters") }
                return
            }
        }

        _uiState.update { it.copy(loginInProgress = true, loginError = null, loginSuccess = false) }

        viewModelScope.launch {
            try {
                val request = LoginRequest(
                    email = currentState.email,
                    password = currentState.password
                )
                Log.d("NetworkDebug", "Attempting login to: ${RetrofitClient.BASE_URL}")
                Log.d("NetworkDebug", "Request payload: ${Gson().toJson(request)}")
                Log.d("LoginViewModel", "Attempting login with email: ${currentState.email}")
                val response = apiService.login(request)
                Log.d("LoginViewModel", "Received response: $response")
//                Log.d("NetworkDebug", "Response: ${response.code()} - ${response.body()}")

                if (response.success) {
                    response.token?.let { token ->
                        tokenManager.saveToken(token)
                        Log.d("LoginViewModel", "Token saved successfully")
                        Log.d("NetworkDebug", "Token saved: ${token.take(5)}...")
                    }
                    _uiState.update {
                        it.copy(
                            loginInProgress = false,
                            loginSuccess = true,
                            authToken = response.token
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loginInProgress = false,
                            loginError = response.message ?: "Login failed. Please try again."
                        )
                    }
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Invalid credentials"
                    404 -> "User not found"
                    500 -> "Server error"
                    else -> "Network error occurred (HTTP ${e.code()})"
                }
                Log.e("LoginViewModel", "HTTP error: $errorMessage", e)
                _uiState.update {
                    it.copy(
                        loginInProgress = false,
                        loginError = errorMessage
                    )
                }
            } catch (e: IOException) {
                Log.e("LoginViewModel", "Network error: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        loginInProgress = false,
                        loginError = "Network error. Please check your connection."
                    )
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Unexpected error: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        loginInProgress = false,
                        loginError = "An unexpected error occurred. Please try again."
                    )
                }
            }
        }
    }

    fun resetLoginStatus() {
        _uiState.update { it.copy(loginSuccess = false, loginError = null) }
    }
}