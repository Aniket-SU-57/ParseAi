package com.adityaproj.parseai.Jobconfig

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.InputChipDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Configurationn(navController: NavController,modifier: Modifier = Modifier
                   ) {
    val scrollState = rememberScrollState()

    var languageInput by rememberSaveable { mutableStateOf("") }
    val languages = remember { mutableStateListOf<String>() }

    var toolsInput by rememberSaveable { mutableStateOf("") }
    val tools = remember { mutableStateListOf<String>() }

    var certInput by rememberSaveable { mutableStateOf("") }
    val certifications = remember { mutableStateListOf<String>() }

    var experience by rememberSaveable { mutableStateOf(0f) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }

    // Proper async handling
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1500)
            isLoading = false
            isSuccess = true
            delay(1500)
            isSuccess = false
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
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            Text("Define", color = Color.White, fontSize = 32.sp)
            Text("Job Configuration", color = Color.White, fontSize = 32.sp)

            Spacer(modifier = Modifier.height(24.dp))

            ChipInputSection(
                title = "Required Language",
                items = languages,
                inputValue = languageInput,
                onInputChange = { languageInput = it },
                onAddItem = {
                    val value = languageInput.trim()
                    if (value.isNotEmpty() && !languages.contains(value)) {
                        languages.add(value)
                    }
                    languageInput = ""
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ChipInputSection(
                title = "Required Tools",
                items = tools,
                inputValue = toolsInput,
                onInputChange = { toolsInput = it },
                onAddItem = {
                    val value = toolsInput.trim()
                    if (value.isNotEmpty() && !tools.contains(value)) {
                        tools.add(value)
                    }
                    toolsInput = ""
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ChipInputSection(
                title = "Certifications",
                items = certifications,
                inputValue = certInput,
                onInputChange = { certInput = it },
                onAddItem = {
                    val value = certInput.trim()
                    if (value.isNotEmpty() && !certifications.contains(value)) {
                        certifications.add(value)
                    }
                    certInput = ""
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Experience Required: ${experience.toInt()} Years",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = experience / 20f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = Color(0xFF2563EB),
                trackColor = Color(0xFF1E3A8A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = experience,
                onValueChange = { experience = it },
                valueRange = 0f..20f,
                steps = 19
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { isLoading = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Setting Configuration...")
                } else {
                    Text("Set Configuration")
                }
            }

            AnimatedVisibility(visible = isSuccess) {
                Text(
                    text = "Configuration Set Successfully ✅",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipInputSection(
    title: String,
    items: MutableList<String>,
    inputValue: String,
    onInputChange: (String) -> Unit,
    onAddItem: () -> Unit
) {

    Text(text = title, color = Color.LightGray)
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .background(
                color = Color(0xFF0F172A),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                Color(0xFF1E3A8A),
                RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items.forEach { item ->
                InputChip(
                    selected = true,
                    onClick = { items.remove(item) },
                    label = { Text(item, color = Color.White) },
                    colors = InputChipDefaults.inputChipColors(
                        containerColor = Color(0xFF2563EB),
                        selectedContainerColor = Color(0xFF2563EB)
                    )
                )
            }

            OutlinedTextField(
                value = inputValue,
                onValueChange = onInputChange,
                placeholder = { Text("Press Enter") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onAddItem() }
                ),
                modifier = Modifier.widthIn(min = 100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigurationnPreview() {
    val navController = rememberNavController()
    Configurationn(navController = navController)
}