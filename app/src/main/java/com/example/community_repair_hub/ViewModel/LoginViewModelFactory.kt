package com.example.community_repair_hub.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.community_repair_hub.Utills.TokenManager
import com.example.community_repair_hub.data.network.repository.AuthRepository

class LoginViewModelFactory(
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(tokenManager, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}