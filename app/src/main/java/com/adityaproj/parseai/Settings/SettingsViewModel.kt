package com.adityaproj.parseai.Settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adityaproj.parseai.Auth.TokenManager
import com.adityaproj.parseai.repository.AuthRepository
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository()
    private val tokenManager = TokenManager(application)

    fun logout(onSuccess: () -> Unit) {

        viewModelScope.launch {

            try {

                val access = tokenManager.getToken()
                val refresh = tokenManager.getRefreshToken()

                // Call API if tokens exist
                if (access != null && refresh != null) {
                    repository.logout(refresh, access)
                }

                // Always clear local tokens
                tokenManager.clearToken()

                // Navigate to login
                onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()

                // Even if API fails, logout locally
                tokenManager.clearToken()
                onSuccess()
            }

        }

    }

}