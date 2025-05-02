package com.example.community_repair_hub.viewModel



import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.community_repair_hub.Components.ImageUploadUtil
import com.example.community_repair_hub.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.IOException

class ReportIssueViewModel(private val app: Application) : AndroidViewModel(app) {
    var uploadedImageUrl: String? = null

    fun uploadImage(uri: Uri, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val imagePart = ImageUploadUtil.uriToMultipart(app, uri)
                val response: ResponseBody = RetrofitClient.instance.uploadImage(imagePart)
                val responseString = response.string()
                uploadedImageUrl = responseString
                onSuccess(responseString)
            } catch (e: IOException) {
                onError("Upload failed: ${e.message}")
            }
        }
    }
}
