package com.example.community_repair_hub.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.Utills.TokenManager
import com.example.community_repair_hub.data.network.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "LoginViewModel"

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginInProgress: Boolean = false,
    val loginError: String? = null,
    val loginSuccess: Boolean = false,
    val authToken: String? = null,
    val userRole: String? = null
)

class LoginViewModel(
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, loginError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, loginError = null) }
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(loginError = "Please fill in all fields") }
            return
        }

        if (!currentState.email.contains("@")) {
            _uiState.update { it.copy(loginError = "Invalid email format") }
            return
        }

        _uiState.update {
            it.copy(
                loginInProgress = true,
                loginError = null,
                loginSuccess = false
            )
        }

        viewModelScope.launch {
            try {
                Log.d(TAG, "Attempting login for email: ${currentState.email}")
                when (val result = authRepository.login(
                    email = currentState.email,
                    password = currentState.password

                )) {
                    is AuthRepository.AuthResult.Success -> {
                        Log.d(TAG, "Login successful")
                        result.data.token?.let { token ->
                            Log.d(TAG, "Saving token")
                            tokenManager.saveToken(token)
                        }
                        result.data.user?.role?.let { role ->
                            Log.d(TAG, "Saving user role: $role")
                            tokenManager.saveRole(role)
                        }
                        _uiState.update {
                            it.copy(
                                loginInProgress = false,
                                loginSuccess = true,
                                authToken = result.data.token,
                                userRole = result.data.user?.role
                            )
                        }

                    }

                    is AuthRepository.AuthResult.Error -> {
                        Log.e(TAG, "Login Failed: ${result.exception.message}", result.exception)
                        _uiState.update {
                            it.copy(
                                loginInProgress = false,
                                loginError = result.exception.message ?: "Login failed"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error during login", e)
                _uiState.update {
                    it.copy(
                        loginInProgress = false,
                        loginError = "An unexpected error occurred: ${e.message}"
                    )
                }
            }
        }
    }

    fun resetLoginStatus() {
        _uiState.update {
            it.copy(
                loginSuccess = false,
                loginError = null,
                authToken = null
            )
        }
    }
}