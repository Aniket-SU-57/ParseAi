package com.adityaproj.parseai.Auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adityaproj.parseai.Navigations.AppRoute
import com.adityaproj.parseai.R
import com.adityaproj.parseai.authviewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {

    var selectedTab by remember { mutableStateOf("login") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // React to auth state
    LaunchedEffect(authState) {

        when (authState) {

            is AuthState.Success -> {

                if (selectedTab == "signup") {

                    snackbarHostState.showSnackbar(
                        "Signup successful. Please login."
                    )

                    selectedTab = "login"

                } else {

                    navController.navigate(AppRoute.Dashboard.route) {

                        popUpTo(AppRoute.LoginScreen.route) {
                            inclusive = true
                        }

                    }
                }
            }

            is AuthState.Error -> {

                snackbarHostState.showSnackbar(
                    (authState as AuthState.Error).message
                )

            }

            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF020617),
                            Color(0xFF020617),
                            Color(0xFF0A102A)
                        )
                    )
                )
                .padding(horizontal = 20.dp)
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .widthIn(max = 420.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.robothead),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "ParseAI",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Intelligent resume parsing for modern recruiters",
                    fontSize = 14.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(28.dp))

                LoginSignupToggle(
                    selected = selectedTab,
                    onSelect = { selectedTab = it }
                )

                Spacer(Modifier.height(26.dp))

                // Username
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                AnimatedVisibility(visible = selectedTab == "signup") {

                    Column {

                        Spacer(Modifier.height(14.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    trailingIcon = {

                        val icon =
                            if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff

                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(icon, contentDescription = null)
                        }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                AnimatedVisibility(visible = selectedTab == "signup") {

                    Column {

                        Spacer(Modifier.height(14.dp))

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            placeholder = { Text("Confirm Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                Spacer(Modifier.height(26.dp))

                Button(
                    onClick = {

                        if (selectedTab == "login") {

                            viewModel.login(username, password)

                        } else {

                            viewModel.signup(
                                username,
                                email,
                                password,
                                confirmPassword
                            )

                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    if (authState is AuthState.Loading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            if (selectedTab == "login")
                                "Log In →"
                            else
                                "Sign Up →"
                        )

                    }

                }

                Spacer(Modifier.height(30.dp))

                TextButton(
                    onClick = {
                        navController.navigate(AppRoute.Dashboard.route)
                    }
                ) {

                    Text("Continue as Guest")

                }
            }
        }
    }
}

@Composable
fun LoginSignupToggle(
    selected: String,
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF111827),
                RoundedCornerShape(28.dp)
            )
            .padding(6.dp)
    ) {

        listOf("login" to "Log In", "signup" to "Sign Up")
            .forEach { (key, label) ->

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (selected == key)
                                Color(0xFF2563EB)
                            else
                                Color.Transparent,
                            RoundedCornerShape(22.dp)
                        )
                        .clickable { onSelect(key) }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        label,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )

                }
            }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {

    val navController = rememberNavController()

    MaterialTheme {
        LoginScreen(navController)
    }

}