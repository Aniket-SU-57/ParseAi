package com.adityaproj.parseai.Upload

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.adityaproj.parseai.R
import kotlinx.coroutines.delay

/* -------------------- SCREEN -------------------- */

@Composable
fun ResumeUploadScreen( navController: NavController) {

    val context = LocalContext.current

    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var uploadProgress by remember { mutableStateOf(0f) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadSuccess by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val mimeType = context.contentResolver.getType(uri)

            if (mimeType == "application/pdf") {
                selectedFileName = uri.lastPathSegment ?: "resume.pdf"
                uploadProgress = 0f
                uploadSuccess = false
                isUploading = true
            } else {
                Toast.makeText(
                    context,
                    "Only PDF files are supported",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // ✅ SIDE EFFECT moved to composable scope
    LaunchedEffect(isUploading) {
        if (isUploading) {
            for (i in 1..100) {
                uploadProgress = i / 100f
                delay(20)
            }
            isUploading = false
            uploadSuccess = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(

                Brush.verticalGradient(
                    listOf(
                        Color(0xFF08123B),   // top
                        Color(0xFF061142),
                        Color(0xFF15256E),
                        Color(0xFF020617)
                    )
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Upload your CV",
            fontSize = 26.sp,
            color = Color.White
        )

        Text(
            text = "Get instant AI feedback and detailed analysis for recruiters and engineering roles.",
            fontSize = 17.sp,
            color = Color.White
        )

        UploadBox(
            onBrowseClick = {
                launcher.launch(arrayOf("application/pdf"))
            }
        )

        when {
            uploadSuccess -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),   // keeps layout stable
                    contentAlignment = Alignment.Center
                ) {
                    UploadSuccessAnimation()
                }
            }

            selectedFileName != null -> {
                UploadProgressCard(
                    fileName = selectedFileName!!,
                    progress = uploadProgress,
                    isUploading = isUploading
                )
            }
        }

        Button(
            onClick = { /* Start analysis */ },
            enabled = uploadSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (uploadSuccess) Color(0xFF2563EB)
                    else Color(0xFF2563EB).copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Start Analysis")
        }
    }
}

/* -------------------- UPLOAD BOX -------------------- */

@Composable
fun UploadBox(onBrowseClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .border(
                BorderStroke(2.dp, Color(0xFF2563EB)),
                RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                painter = painterResource(id = R.drawable.cloud),
                contentDescription = null,
                tint = Color(0xFF2563EB),
                modifier = Modifier.size(48.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text("Tap or drag to upload resume", color = Color.White)
            Text("Supports PDF up to 10MB", color = Color.Gray, fontSize = 12.sp)

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onBrowseClick,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Browse Files")
            }
        }
    }
}

/* -------------------- PROGRESS CARD -------------------- */

@Composable
fun UploadProgressCard(
    fileName: String,
    progress: Float,
    isUploading: Boolean
) {
    Card(
        modifier =  Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F172A)
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

            Text(fileName, color = Color.White)

            Spacer(Modifier.height(8.dp))

            LinearProgressIndicator(
                modifier =  Modifier.fillMaxWidth(),
                progress = progress,
                color = Color(0xFF2563EB)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = if (isUploading) "UPLOADING..." else "PROCESSING...",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

/* -------------------- SUCCESS ANIMATION -------------------- */

@Composable
fun UploadSuccessAnimation() {

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "successScale"
    )

    Box(
        modifier = Modifier
            .size(90.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .background(
                Color(0xFF2563EB),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Success",
            tint = Color.White,
            modifier = Modifier.size(42.dp)
        )
    }
}

/* -------------------- PREVIEW -------------------- */

