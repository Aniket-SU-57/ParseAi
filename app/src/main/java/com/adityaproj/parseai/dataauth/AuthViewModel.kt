package com.adityaproj.parseai.authviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adityaproj.parseai.Auth.AuthState
import com.adityaproj.parseai.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState


    // LOGIN
    fun login(username: String, password: String) {

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            try {

                val response = repository.login(username, password)

                if (response.isSuccessful && response.body() != null) {

                    _authState.value =
                        AuthState.Success("Login Successful")

                } else {

                    _authState.value =
                        AuthState.Error("Invalid credentials")

                }

            } catch (e: Exception) {

                _authState.value =
                    AuthState.Error(e.message ?: "Login failed")

            }
        }
    }


    // SIGNUP
    fun signup(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {

        viewModelScope.launch {

            if (password != confirmPassword) {

                _authState.value =
                    AuthState.Error("Passwords do not match")

                return@launch
            }

            _authState.value = AuthState.Loading

            try {

                val response = repository.signup(
                    username,
                    email,
                    password
                )

                if (response.isSuccessful) {

                    _authState.value =
                        AuthState.Success("Signup Successful")

                } else {

                    _authState.value =
                        AuthState.Error("Signup failed")

                }

            } catch (e: Exception) {

                _authState.value =
                    AuthState.Error(e.message ?: "Signup error")

            }
        }
    }
}