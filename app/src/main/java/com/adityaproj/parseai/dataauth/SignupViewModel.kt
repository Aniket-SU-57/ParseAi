package com.adityaproj.parseai.dataauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adityaproj.parseai.Auth.SignupState
import com.adityaproj.parseai.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SignupRepository
) : ViewModel() {

    private val _signupState = MutableStateFlow(SignupState())
    val signupState: StateFlow<SignupState> = _signupState

    fun signup(email: String, password: String) {

        viewModelScope.launch {

            _signupState.value = SignupState(isLoading = true)

            try {

                val response = repository.signup(
                    signupdata(
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful && response.body() != null) {

                    _signupState.value = SignupState(
                        success = response.body()
                    )

                } else {

                    _signupState.value = SignupState(
                        error = "Signup failed"
                    )

                }

            } catch (e: Exception) {

                _signupState.value = SignupState(
                    error = e.message ?: "Unknown error"
                )

            }

        }

    }

}