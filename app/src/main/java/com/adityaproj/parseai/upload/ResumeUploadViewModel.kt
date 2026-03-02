package com.adityaproj.parseai.upload

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adityaproj.parseai.models.UploadResponse
import com.adityaproj.parseai.repository.ResumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val repository: ResumeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    /* ---------------- Upload Screen Validation State ---------------- */

    private val _uploadUiState =
        MutableStateFlow<UploadUiState>(UploadUiState.Idle)
    val uploadUiState = _uploadUiState.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private var selectedUri: Uri? = null

    /* ---------------- Backend Upload State ---------------- */

    private val _uploadState =
        MutableStateFlow<UiState<UploadResponse>>(UiState.Idle)
    val uploadState = _uploadState.asStateFlow()

    private val _resumeId = MutableStateFlow<String?>(null)
    val resumeId = _resumeId.asStateFlow()

    private val _status = MutableStateFlow("UPLOADED")
    val status = _status.asStateFlow()

    /* ---------------- File Validation ---------------- */

    fun validateFile(uri: Uri) {

        _uploadUiState.value = UploadUiState.Validating
        _progress.value = 0f

        viewModelScope.launch {

            for (i in 1..100) {
                delay(8)
                _progress.value = i / 100f
            }

            val type = context.contentResolver.getType(uri)

            if (type == "application/pdf") {
                selectedUri = uri
                _uploadUiState.value = UploadUiState.Ready
            } else {
                _uploadUiState.value =
                    UploadUiState.Error("Only PDF files allowed")
            }
        }
    }

    /* ---------------- Start Upload ---------------- */

    fun startUpload() {

        val uri = selectedUri ?: return

        viewModelScope.launch {

            _uploadState.value = UiState.Loading

            val result = repository.uploadResume(uri)

            result.onSuccess {
                _resumeId.value = it.id
                _uploadState.value = UiState.Success(it)

                // Start polling only if ID exists
                it.id?.let { id ->
                    startStatusPolling(id)
                }
            }

            result.onFailure {
                _uploadState.value =
                    UiState.Error(it.message ?: "Upload failed")
            }
        }
    }

    /* ---------------- Status Polling ---------------- */

    private fun startStatusPolling(id: String) {

        viewModelScope.launch(Dispatchers.IO) {

            repeat(15) {
                delay(3000)

                val newStatus = repository.getResumeStatus(id)
                _status.value = newStatus

                if (newStatus == "COMPLETED") return@launch
            }

            _uploadState.value =
                UiState.Error("Processing timeout")
        }
    }
}