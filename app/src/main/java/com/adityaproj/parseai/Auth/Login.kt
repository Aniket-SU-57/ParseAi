package com.adityaproj.parseai.Auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.adityaproj.parseai.Navigations.AppRoute
import com.adityaproj.parseai.R

@Composable
fun LoginScreen(navController: NavHostController) {

    var selectedTab by remember { mutableStateOf("login") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
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

            // Logo
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
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(28.dp))

            LoginSignupToggle(
                selected = selectedTab,
                onSelect = { selectedTab = it }
            )

            Spacer(Modifier.height(26.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF334155),
                    focusedBorderColor = Color(0xFF2563EB)
                )
            )

            Spacer(Modifier.height(14.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF334155),
                    focusedBorderColor = Color(0xFF2563EB)
                )
            )

            AnimatedVisibility(visible = selectedTab == "signup") {
                Column {
                    Spacer(Modifier.height(14.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("Confirm password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF334155),
                            focusedBorderColor = Color(0xFF2563EB)
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            if (selectedTab == "login") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = Color(0xFF60A5FA),
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            Spacer(Modifier.height(26.dp))

            // Primary CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF2563EB),
                                Color(0xFF1D4ED8)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (selectedTab == "login") "Log In →" else "Sign Up →",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(26.dp))

            // Social Login
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SocialLoginCard(
                    icon = R.drawable.google,
                    text = "Google",
                    modifier = Modifier.weight(1f)
                ) { }

                SocialLoginCard(
                    icon = R.drawable.meta,
                    text = "Meta",
                    modifier = Modifier.weight(1f)
                ) { }

                SocialLoginCard(
                    icon = R.drawable.link,
                    text = "LinkedIn",
                    modifier = Modifier.weight(1f)
                ) { }
            }

            Spacer(Modifier.height(30.dp))
            TextButton(
                onClick = {
                    navController.navigate(AppRoute.Dashboard.route) {
                        popUpTo(AppRoute.LoginScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Text(
                    text = "Continue as Guest",
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp
                )
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
            .background(Color(0xFF111827), RoundedCornerShape(28.dp))
            .padding(6.dp)
    ) {

        listOf("login" to "Log In", "signup" to "Sign Up").forEach { (key, label) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (selected == key) Color(0xFF2563EB) else Color.Transparent,
                        RoundedCornerShape(22.dp)
                    )
                    .clickable { onSelect(key) }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(label, color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun SocialLoginCard(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F172A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = text,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                color = Color.White
            )
        }
    }
}


