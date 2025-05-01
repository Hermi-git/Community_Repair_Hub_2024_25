package com.example.community_repair_hub.viewModel

import android.util.Log // Import Log for debugging
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay // Import delay for simulation

// Define a data class to hold the UI state for the Login screen
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginInProgress: Boolean = false,
    val loginError: String? = null,
    val loginSuccess: Boolean = false,
    val authToken: String? = null // To store the token upon successful login
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Get the ApiService instance (consider using Dependency Injection)
    // private val apiService = RetrofitClient.instance // Keep this commented out for frontend-only testing

    /**
     * Updates the email state.
     */
    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, loginError = null) } // Clear error on input change
    }

    /**
     * Updates the password state.
     */
    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, loginError = null) } // Clear error on input change
    }

    /**
     * Initiates the login process.
     * Performs validation and simulates a successful login if validation passes (FRONTEND ONLY).
     */
    fun login() {
        val currentState = _uiState.value

        // 1. Enhanced Validation
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(loginError = "Email and password cannot be empty") }
            return
        }
        if (!currentState.email.contains("@")) { // Check if email contains '@'
            _uiState.update { it.copy(loginError = "Invalid email format (must contain '@')") }
            return
        }
        if (currentState.password.length < 6) { // Check password length
            _uiState.update { it.copy(loginError = "Password must be at least 6 characters long") }
            return
        }

        // 2. Set loading state and clear previous errors/success
        _uiState.update { it.copy(loginInProgress = true, loginError = null, loginSuccess = false) }

        // 3. Simulate a short delay and then set success (FRONTEND ONLY)
        viewModelScope.launch {
            delay(1000) // Simulate a small delay like a network call would have
            _uiState.update {
                it.copy(
                    loginInProgress = false,
                    loginSuccess = true,
                    authToken = "dummy_token_frontend_only" // Provide a dummy token
                )
            }
        }

        /* // --- Original Backend Call Logic (Commented Out) ---
        // 3. Launch coroutine for network call
        viewModelScope.launch {
            try {
                // 4. Create LoginRequest
                val request = LoginRequest(
                    email = currentState.email,
                    password = currentState.password
                )

                // 5. Call apiService.login(request)
                // val response = apiService.login(request) // Network call commented out

                // 6. Handle response
                // if (response.success) {
                //     // Login successful, update state with token and success flag
                //     _uiState.update {
                //         it.copy(
                //             loginInProgress = false,
                //             loginSuccess = true,
                //             authToken = response.token // Store the token
                //         )
                //     }
                //     // TODO: Persist the token securely (e.g., EncryptedSharedPreferences, Keystore)
                // } else {
                //     // Login failed, update state with error message
                //     _uiState.update {
                //         it.copy(
                //             loginInProgress = false,
                //             loginError = response.message ?: "Login failed. Please check credentials."
                //         )
                //     }
                // }

            } catch (e: Exception) {
                // 7. Handle exceptions (network errors, etc.)
                Log.e("LoginViewModel", "Login failed with exception", e) // Log the error
                _uiState.update {
                    it.copy(
                        loginInProgress = false,
                        loginError = e.message ?: "An unexpected error occurred during login."
                    )
                }
            }
        }
        */ // --- End of Original Backend Call Logic ---
    }

    /**
     * Resets the login status flags (error and success).
     * Should be called after the UI has reacted to a login attempt (e.g., shown a message or navigated).
     */
    fun resetLoginStatus() {
        _uiState.update { it.copy(loginSuccess = false, loginError = null) }
    }
}

