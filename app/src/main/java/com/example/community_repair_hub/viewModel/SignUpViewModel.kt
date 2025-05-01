package com.example.community_repair_hub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.RetrofitClient
import com.example.community_repair_hub.data.network.model.SignupRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Define a data class to hold the UI state
data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val selectedRole: String = "Citizen",
    val selectedRegion: String = "",
    val selectedCity: String = "",
    val isRegionDropdownExpanded: Boolean = false,
    val isCityDropdownExpanded: Boolean = false,
    val regions: List<String> = listOf("Region A", "Region B", "Region C", "Region D"), // Example data, consider moving to repository
    val cities: List<String> = listOf("city A", "city B", "city C", "city D"), // Example data, consider moving to repository
    val signupInProgress: Boolean = false,
    val signupError: String? = null,
    val signupSuccess: Boolean = false
)

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()
    private val apiService = RetrofitClient.instance

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName, signupError = null) } // Clear error on input change
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, signupError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, signupError = null) }
    }

    fun onRoleSelected(role: String) {
        _uiState.update { it.copy(selectedRole = role, signupError = null) }
    }

    fun onRegionSelected(region: String) {
        _uiState.update { it.copy(selectedRegion = region, isRegionDropdownExpanded = false, signupError = null) }
        // TODO: Potentially load/filter cities based on the selected region here if needed
        // Example: Load cities for the selected region from a repository
        // _uiState.update { it.copy(cities = repository.getCitiesForRegion(region)) }
    }

    fun onCitySelected(city: String) {
        _uiState.update { it.copy(selectedCity = city, isCityDropdownExpanded = false, signupError = null) }
    }

    fun toggleRegionDropdown(expanded: Boolean? = null) {
        val newState = expanded ?: !_uiState.value.isRegionDropdownExpanded
        _uiState.update { it.copy(isRegionDropdownExpanded = newState) }
    }

    fun toggleCityDropdown(expanded: Boolean? = null) {
        val newState = expanded ?: !_uiState.value.isCityDropdownExpanded
        _uiState.update { it.copy(isCityDropdownExpanded = newState) }
    }

    fun signup() {
        _uiState.update { it.copy(signupInProgress = true, signupError = null, signupSuccess = false) }
        val currentState = _uiState.value

        // --- Validation (Keep your existing validation) ---
        if (currentState.name.isBlank() || currentState.email.isBlank() || currentState.password.isBlank() || currentState.selectedRegion.isBlank() || currentState.selectedCity.isBlank()) {
            _uiState.update { it.copy(signupInProgress = false, signupError = "Please fill all fields") }
            return
        }
        // --- Add more specific validation if needed ---

        viewModelScope.launch {
            try {
                // 1. Create the request object from the current UI state
                val request = SignupRequest(
                    name = currentState.name,
                    email = currentState.email,
                    password = currentState.password, // IMPORTANT: Consider security - ideally hash password on backend
                    role = currentState.selectedRole,
                    region = currentState.selectedRegion,
                    city = currentState.selectedCity
                )

                // 2. Make the actual network call using the ApiService
                val response = apiService.signup(request)

                // 3. Process the response
                if (response.success) {
                    _uiState.update { it.copy(signupInProgress = false, signupSuccess = true) }
                    // Optionally reset fields after successful signup
                    // _uiState.update { SignupUiState() }
                } else {
                    // Use the error message from the backend response
                    _uiState.update { it.copy(signupInProgress = false, signupError = response.message ?: "Signup failed. Please try again.") }
                }

            } catch (e: Exception) {
                // 4. Handle exceptions (network errors, JSON parsing errors, etc.)
                // Log the error for debugging: Log.e("SignupViewModel", "Signup failed", e)
                _uiState.update { it.copy(signupInProgress = false, signupError = e.message ?: "An network error occurred") }
            }
        }
    }


    // Function to be called by the UI to clear the signup status (e.g., after navigating away or showing a message)
    fun resetSignupStatus() {
        _uiState.update { it.copy(signupSuccess = false, signupError = null) }
    }
}

