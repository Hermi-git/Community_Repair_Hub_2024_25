package com.example.community_repair_hub.Components

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

object ImageUploadUtil {
    fun uriToMultipart(context: Context, uri: Uri, paramName: String = "image"): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { output ->
            inputStream?.copyTo(output)
        }
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), tempFile)
        return MultipartBody.Part.createFormData(paramName, tempFile.name, requestFile)
    }
}
