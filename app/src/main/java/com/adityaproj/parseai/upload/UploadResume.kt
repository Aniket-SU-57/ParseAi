package com.adityaproj.parseai.upload

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adityaproj.parseai.Navigations.BottomRoute

/* ------------------------------------------------ */
/* REAL SCREEN (Connected to ViewModel + Launcher) */
/* ------------------------------------------------ */

@Composable
fun ResumeUploadScreen(
    navController: NavController,
    viewModel: ResumeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uploadUiState.collectAsState()
    val progress by viewModel.progress.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.validateFile(it)
        }
    }

    ResumeUploadContent(
        uiState = uiState,
        progress = progress,
        onBrowseClick = {
            launcher.launch("*/*")
        },
        onStartAnalysis = {
            viewModel.startUpload()
            navController.navigate(BottomRoute.LoaderScreen.route)
        }
    )
}

/* ------------------------------------------------ */
/* UI CONTENT (Stateless + Preview Friendly)       */
/* ------------------------------------------------ */

@Composable
fun ResumeUploadContent(
    uiState: UploadUiState,
    progress: Float,
    onBrowseClick: () -> Unit,
    onStartAnalysis: () -> Unit
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(600),
        label = "progressAnim"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF08123B),
                        Color(0xFF061142),
                        Color(0xFF15256E),
                        Color(0xFF020617)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.06f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedContent(
                    targetState = uiState,
                    label = "stateAnimation"
                ) { state ->

                    when (state) {

                        UploadUiState.Idle -> {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Icon(
                                    imageVector = Icons.Default.CloudUpload,
                                    contentDescription = null,
                                    tint = Color(0xFF60A5FA),
                                    modifier = Modifier.size(80.dp)
                                )

                                Spacer(Modifier.height(20.dp))

                                Text(
                                    "Upload Your Resume",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    "PDF format only",
                                    color = Color.White.copy(alpha = 0.6f)
                                )

                                Spacer(Modifier.height(32.dp))

                                Button(
                                    onClick = onBrowseClick,
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Browse File")
                                }
                            }
                        }

                        UploadUiState.Validating -> {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                CircularProgressIndicator(
                                    color = Color(0xFF60A5FA)
                                )

                                Spacer(Modifier.height(24.dp))

                                Text(
                                    "Validating file...",
                                    color = Color.White
                                )

                                Spacer(Modifier.height(16.dp))

                                LinearProgressIndicator(
                                    progress = animatedProgress,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color(0xFF60A5FA),
                                    trackColor = Color.White.copy(alpha = 0.1f)
                                )
                            }
                        }

                        UploadUiState.Ready -> {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF22C55E),
                                    modifier = Modifier.size(80.dp)
                                )

                                Spacer(Modifier.height(20.dp))

                                Text(
                                    "File Ready for Analysis",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(Modifier.height(32.dp))

                                Button(
                                    onClick = onStartAnalysis,
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Start Analysis")
                                }
                            }
                        }

                        is UploadUiState.Error -> {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(60.dp)
                                )

                                Spacer(Modifier.height(16.dp))

                                Text(
                                    state.message,
                                    color = Color.Red
                                )

                                Spacer(Modifier.height(24.dp))

                                Button(
                                    onClick = onBrowseClick,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Try Again")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/* ------------------------------------------------ */
/* PREVIEWS                                         */
/* ------------------------------------------------ */

@Preview(showBackground = true)
@Composable
fun UploadIdlePreview() {
    ResumeUploadContent(
        uiState = UploadUiState.Idle,
        progress = 0f,
        onBrowseClick = {},
        onStartAnalysis = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UploadValidatingPreview() {
    ResumeUploadContent(
        uiState = UploadUiState.Validating,
        progress = 0.65f,
        onBrowseClick = {},
        onStartAnalysis = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UploadReadyPreview() {
    ResumeUploadContent(
        uiState = UploadUiState.Ready,
        progress = 1f,
        onBrowseClick = {},
        onStartAnalysis = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UploadErrorPreview() {
    ResumeUploadContent(
        uiState = UploadUiState.Error("Only PDF files allowed"),
        progress = 0f,
        onBrowseClick = {},
        onStartAnalysis = {}
    )
}