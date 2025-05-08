package com.example.community_repair_hub.Data.network.Repository


import com.example.community_repair_hub.data.network.ApiService
import com.example.community_repair_hub.data.network.model.LoginRequest
import com.example.community_repair_hub.data.network.model.LoginResponse
import com.example.community_repair_hub.data.network.model.SignupRequest
import com.example.community_repair_hub.data.network.model.SignupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class AuthRepository(private val apiService: ApiService) {

    sealed class AuthResult<out T> {
        data class Success<out T>(val data: T) : AuthResult<T>()
        data class Error(val exception: Throwable) : AuthResult<Nothing>()
    }
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        role:String,
        region:String,
        city:String
    ): AuthResult<SignupResponse> = withContext(Dispatchers.IO) {
        try {
            val request = SignupRequest(name, email,password,role,region,city)
            val response = apiService.signup(request)
            AuthResult.Success(response)
        } catch (e: IOException) {
            AuthResult.Error(e)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }
    suspend fun login(
        email: String,
        password: String
    ): AuthResult<LoginResponse> = withContext(Dispatchers.IO) {
        try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            AuthResult.Success(response)
        } catch (e: IOException) {
            AuthResult.Error(e)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }
}