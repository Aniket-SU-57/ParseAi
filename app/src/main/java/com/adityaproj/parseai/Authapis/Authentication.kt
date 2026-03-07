package com.adityaproj.parseai.Authapis

import com.adityaproj.parseai.dataauth.Signupresponse
import com.adityaproj.parseai.dataauth.signupdata
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Authentication {
    @POST("/auth/signup/")
    suspend fun sign(
        @Body request: signupdata
    ): Response<Signupresponse>

    @GET("/api/auth/login/")
    suspend fun login(

    )
}

