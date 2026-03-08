package com.adityaproj.parseai.repository

import com.adityaproj.parseai.Authapis.AuthRetrofit
import com.adityaproj.parseai.dataauth.LoginRequest
import com.adityaproj.parseai.dataauth.LogoutRequest
import com.adityaproj.parseai.dataauth.signupdata


class AuthRepository {

    suspend fun login(username: String, password: String) =
        AuthRetrofit.api.loginUser(
            LoginRequest(username, password)
        )


    suspend fun signup(
        username: String,
        email: String,
        password: String
    ) =
        AuthRetrofit.api.sign(
            signupdata(username, email, password)
        )

    suspend fun logout(
        refreshToken: String,
        accessToken: String
    ) =
        AuthRetrofit.api.logoutUser(
            LogoutRequest(refreshToken),
            "Bearer $accessToken"
        )
}