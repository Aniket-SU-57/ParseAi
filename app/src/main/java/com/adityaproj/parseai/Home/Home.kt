package com.adityaproj.parseai.Home

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adityaproj.parseai.Navigations.BottomRoute
import com.adityaproj.parseai.R

/* -------------------- HOME SCREEN -------------------- */

@Composable
fun Home(navController: NavHostController) {

    val activities = listOf(
        Activity("John Doe", "Backend Engineer", "98%", "2h ago"),
        Activity("Sarah Smith", "Data Scientist", "85%", "5h ago"),
        Activity("Mike Ross", "Legal Intern", "40%", "1d ago")
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
    ) {

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { Header() }
            item { StatsCard() }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MiniCard(
                        icon = R.drawable.robothead,
                        title = "Top Skill",
                        value = "Python",
                        modifier = Modifier.weight(1f)
                    )

                    MiniCard(
                        icon = R.drawable.robothead,
                        title = "Pending",
                        value = "8 Profiles",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    ActionButton(
                        title = "Upload Resume",
                        icon = R.drawable.robothead,
                        onClick = {
                            navController.navigate(BottomRoute.UploadResume.route)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    ActionButton(
                        title = "Configure Job",
                        icon = R.drawable.robothead,
                        onClick = {
                            navController.navigate(BottomRoute.Configur.route)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Recent Activity",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "See all →",
                        color = Color(0xFF60A5FA),
                        modifier = Modifier.clickable { }
                    )
                }
            }

            items(activities) {
                ActivityItem(it)
            }
        }
    }
}

/* -------------------- STATS CARD -------------------- */

@Composable
fun StatsCard() {

    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    }
                )
            },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.12f),
                            Color.White.copy(alpha = 0.04f)
                        )
                    ),
                    RoundedCornerShape(22.dp)
                )
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text("Resumes Parsed", color = Color.White)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "142",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Updated just now",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }

                Image(
                    painter = painterResource(R.drawable.robothead),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}

/* -------------------- MINI CARD -------------------- */

@Composable
fun MiniCard(
    icon: Int,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.12f),
                            Color.White.copy(alpha = 0.04f)
                        )
                    ),
                    RoundedCornerShape(20.dp)
                )
                .padding(14.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )

                Column {
                    Text(title, color = Color(0xFF94A3B8), fontSize = 13.sp)
                    Text(value, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/* -------------------- GLASS ACTION BUTTON -------------------- */

@Composable
fun ActionButton(
    title: String,
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    Card(
        modifier = modifier
            .height(90.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                        onClick()
                    }
                )
            },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.15f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    ),
                    RoundedCornerShape(22.dp)
                )
                .padding(horizontal = 18.dp),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}


@Composable
fun ActivityItem(activity: Activity) {

    val matchValue = activity.match.replace("%", "").toInt()
    val matchColor = when {
        matchValue >= 90 -> Color(0xFF22C55E)
        matchValue >= 70 -> Color(0xFF60A5FA)
        else -> Color(0xFFFACC15)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(R.drawable.robothead),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(activity.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(activity.role, color = Color(0xFF94A3B8), fontSize = 13.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(activity.match, color = matchColor, fontWeight = FontWeight.Bold)
                Text(activity.time, color = Color(0xFF94A3B8), fontSize = 12.sp)
            }
        }
    }
}

/* -------------------- HEADER -------------------- */

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.robothead),
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .background(Color.Gray, CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("WELCOME BACK", color = Color(0xFF94A3B8), fontSize = 12.sp)
        }

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = Color.White
        )
    }
}

/* -------------------- DATA -------------------- */

data class Activity(
    val name: String,
    val role: String,
    val match: String,
    val time: String
)

/* -------------------- PREVIEW -------------------- */

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    Home(navController = navController)
}