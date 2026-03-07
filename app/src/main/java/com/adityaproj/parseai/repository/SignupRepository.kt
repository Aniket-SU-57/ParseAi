package com.adityaproj.parseai.repository

import com.adityaproj.parseai.Authapis.Authentication
import com.adityaproj.parseai.dataauth.Signupresponse
import com.adityaproj.parseai.dataauth.signupdata
import retrofit2.Response
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val api: Authentication
) {

    suspend fun signup(request: signupdata): Response<Signupresponse> {
        return api.sign(request)
    }

}