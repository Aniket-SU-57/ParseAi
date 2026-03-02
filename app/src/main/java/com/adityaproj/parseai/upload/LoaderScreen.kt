package com.adityaproj.parseai.upload

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay

data class ProgressStep(
    val label: String,
    val icon: ImageVector
)

val steps = listOf(
    ProgressStep("File Uploaded", Icons.Default.CloudUpload),
    ProgressStep("File Parsed", Icons.Default.Description),
    ProgressStep("ML Processing", Icons.Default.Memory),
    ProgressStep("Generating Results", Icons.Default.Check)
)

@Composable
fun LoaderScreenload(
    navController: NavController,
    viewModel: ResumeViewModel = hiltViewModel()
) {

    val status by viewModel.status.collectAsState()
    val resumeId by viewModel.resumeId.collectAsState()

    val steps = listOf(
        "File Uploaded",
        "Parsing Resume",
        "AI Processing",
        "Generating Results"
    )

    val completedSteps = when (status) {
        "UPLOADED" -> 1
        "PARSING" -> 2
        "ML_PROCESSING" -> 3
        "GENERATING_RESULTS" -> 4
        "COMPLETED" -> 4
        else -> 0
    }

    val progress = completedSteps / 4f

    // Auto navigate when done
    LaunchedEffect(status) {
        if (status == "COMPLETED" && resumeId != null) {
            delay(800)
            navController.navigate("result_screen/$resumeId")
        }
    }

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
            .padding(24.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            /* ---------------- TOP CIRCULAR PROGRESS ---------------- */

            Box(contentAlignment = Alignment.Center) {

                val animatedProgress by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(700),
                    label = "circularAnim"
                )

                CircularProgressIndicator(
                    progress = animatedProgress,
                    strokeWidth = 10.dp,
                    modifier = Modifier.size(150.dp),
                    color = Color(0xFF60A5FA),
                    trackColor = Color.White.copy(alpha = 0.1f)
                )

                // Shimmer glow effect
                val infiniteTransition = rememberInfiniteTransition()
                val shimmerAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.4f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1200),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "shimmer"
                )

                Icon(
                    imageVector = Icons.Default.Memory,
                    contentDescription = null,
                    tint = Color(0xFF60A5FA).copy(alpha = shimmerAlpha),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(Modifier.height(48.dp))

            /* ---------------- VERTICAL STEP PROGRESS ---------------- */

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {

                steps.forEachIndexed { index, label ->

                    val isCompleted = index < completedSteps
                    val isCurrent = index == completedSteps - 1

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .background(
                                        color = when {
                                            isCompleted -> Color(0xFF22C55E)
                                            isCurrent -> Color(0xFF60A5FA)
                                            else -> Color.White.copy(alpha = 0.3f)
                                        },
                                        shape = CircleShape
                                    )
                            )

                            if (index != steps.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(40.dp)
                                        .background(
                                            if (index < completedSteps - 1)
                                                Color(0xFF22C55E)
                                            else
                                                Color.White.copy(alpha = 0.1f)
                                        )
                                )
                            }
                        }

                        Spacer(Modifier.width(16.dp))

                        Text(
                            text = label,
                            color = when {
                                isCompleted -> Color.White
                                isCurrent -> Color(0xFF60A5FA)
                                else -> Color.White.copy(alpha = 0.5f)
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(Modifier.height(12.dp))
                }
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Analyzing your resume with AI...",
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}