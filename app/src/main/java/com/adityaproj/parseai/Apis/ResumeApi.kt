package com.adityaproj.parseai.Apis

import com.adityaproj.parseai.models.StatusResponse
import com.adityaproj.parseai.models.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ResumeApi {

    @Multipart
    @POST("resumes/upload")
    suspend fun uploadResume(
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>

    @GET("resume/{id}/status")
    suspend fun getResumeStatus(
        @Path("id") id: String
    ): Response<StatusResponse>

}
