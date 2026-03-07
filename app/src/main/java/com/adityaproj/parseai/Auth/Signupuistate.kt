package com.adityaproj.parseai.Auth

import com.adityaproj.parseai.dataauth.Signupresponse


data class SignupState(

    val isLoading: Boolean = false,

    val success: Signupresponse? = null,

    val error: String? = null

)