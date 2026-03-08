package com.adityaproj.parseai.Auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    // Save both tokens
    suspend fun saveTokens(access: String, refresh: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN_KEY] = access
            it[REFRESH_TOKEN_KEY] = refresh
        }
    }

    // Get access token
    suspend fun getToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[ACCESS_TOKEN_KEY]
    }

    // Get refresh token
    suspend fun getRefreshToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[REFRESH_TOKEN_KEY]
    }

    // Clear tokens on logout
    suspend fun clearToken() {
        context.dataStore.edit {
            it.remove(ACCESS_TOKEN_KEY)
            it.remove(REFRESH_TOKEN_KEY)
        }
    }
}