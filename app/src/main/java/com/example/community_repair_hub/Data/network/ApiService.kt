package com.example.community_repair_hub.data.network


import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.data.network.model.LoginRequest
import com.example.community_repair_hub.data.network.model.LoginResponse
import com.example.community_repair_hub.data.network.model.SignupRequest
import com.example.community_repair_hub.data.network.model.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("users/register")
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse


    @GET("issues/search/category")
    suspend fun searchIssuesByCategory(
        @Query("category") category: String
    ): Response<List<IssueResponse>>

    @GET("issues/search/location")
    suspend fun searchIssuesByLocation(
        @Query("location") location: String
    ): Response<List<IssueResponse>>

    @Multipart
    @POST("api/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ResponseBody>

    @Multipart
    @POST("api/reports")
    suspend fun submitReport(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<ResponseBody>

    @GET("citizens/issues")
    suspend fun getIssues(): Response<List<IssueResponse>>

}
