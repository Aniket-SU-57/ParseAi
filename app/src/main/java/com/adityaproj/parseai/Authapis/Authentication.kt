package com.adityaproj.parseai.Authapis

import com.adityaproj.parseai.dataauth.Signupresponse
import com.adityaproj.parseai.dataauth.signupdata
import com.adityaproj.parseai.models.StatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Authentication {
    @POST("/auth/signup/")
    suspend fun sign(
        @Body request: signupdata
    ): Response<Signupresponse>
}