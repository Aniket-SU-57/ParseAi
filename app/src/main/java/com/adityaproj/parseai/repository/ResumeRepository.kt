package com.adityaproj.parseai.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.adityaproj.parseai.Apis.ResumeApi
import com.adityaproj.parseai.models.UploadResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ResumeRepository @Inject constructor(
    private val api: ResumeApi,
    @ApplicationContext private val context: Context
) {

    suspend fun uploadResume(uri: Uri): Result<UploadResponse> {
        return try {


            val filePart = uriToMultipart(uri)

            val response = api.uploadResume(filePart)

            Log.d("UPLOAD_DEBUG", "Code: ${response.code()}")
            Log.d("UPLOAD_DEBUG", "Body: ${response.body()}")

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Upload failed"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getResumeStatus(id: String): String {
        return try {

            val response = api.getResumeStatus(id)

            if (response.isSuccessful) {
                response.body()?.status ?: "UNKNOWN"
            } else {
                "FAILED"
            }

        } catch (e: Exception) {
            "ERROR"
        }
    }

    private fun uriToMultipart(uri: Uri): MultipartBody.Part {

        val file = File(context.cacheDir, "resume.pdf")

        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val requestBody =
            file.asRequestBody("application/pdf".toMediaType())

        return MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestBody
        )
    }
}