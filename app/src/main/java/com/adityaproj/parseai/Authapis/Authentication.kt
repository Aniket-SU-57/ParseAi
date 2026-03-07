package com.adityaproj.parseai.Authapis

import com.adityaproj.parseai.dataauth.LoginRequest
import com.adityaproj.parseai.dataauth.LoginResponse
import com.adityaproj.parseai.dataauth.Signupresponse
import com.adityaproj.parseai.dataauth.signupdata
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST


interface Authentication {
    @POST("/api/auth/signup/")
    suspend fun sign(
        @Body request: signupdata
    ): Response<Signupresponse>

    @POST("/api/auth/login/")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}

