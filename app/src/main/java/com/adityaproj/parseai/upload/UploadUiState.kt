package com.adityaproj.parseai.upload

sealed class UploadUiState {
    object Idle : UploadUiState()
    object Validating : UploadUiState()
    object Ready : UploadUiState()
    data class Error(val message: String) : UploadUiState()
}