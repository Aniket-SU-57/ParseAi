
package com.adityaproj.parseai.History

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adityaproj.parseai.Home.Activity
import com.adityaproj.parseai.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {

    val activities = listOf(
        Activity("John Doe", "Backend Engineer", "98%", "2h ago"),
        Activity("Sarah Smith", "Data Scientist", "85%", "5h ago"),
        Activity("Mike Ross", "Legal Intern", "40%", "1d ago")
    )

    var searchQuery by remember { mutableStateOf("") }
    
    var debouncedQuery by remember { mutableStateOf("") }

    var isActive by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(searchQuery) {
        delay(300L)           
        debouncedQuery = searchQuery
    }


    val filteredActivities = remember(debouncedQuery) {
        if (debouncedQuery.isBlank()) activities
        else activities.filter {
            it.name.contains(debouncedQuery, ignoreCase = true) ||
                    it.role.contains(debouncedQuery, ignoreCase = true)
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
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            SearchBar(
                query = searchQuery,           // Shows raw input instantly
                onQueryChange = {
                    searchQuery = it           // Updates raw input immediately
                },
                onSearch = {
                    debouncedQuery = it
                    isActive = false
                },
                active = isActive,
                onActiveChange = {
                    isActive = it
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White.copy(alpha = 0.08f)
                ),
                placeholder = {
                    Text("Search activities", color = Color(0xFFCBD5E1))
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFFCBD5E1)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            debouncedQuery = ""   // Clear immediately, no debounce needed
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            ) {}

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "YOUR RECENT ACTIVITIES",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredActivities) { activity ->
                    Minicards(activity)
                }

                if (filteredActivities.isEmpty()) {
                    item {
                        Text(
                            text = "No matching activities",
                            color = Color(0xFF94A3B8),
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Minicards(
    activity: Activity,
    modifier: Modifier = Modifier
) {
    val matchValue = activity.match.replace("%", "").toInt()
    val matchColor = when {
        matchValue >= 90 -> Color(0xFF22C55E)
        matchValue >= 70 -> Color(0xFF60A5FA)
        else -> Color(0xFFFACC15)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.robothead),
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(activity.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(activity.role, color = Color(0xFFCBD5E1), fontSize = 13.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(activity.match, color = matchColor, fontWeight = FontWeight.Bold)
                Text(activity.time, color = Color(0xFFCBD5E1), fontSize = 12.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen()
    }
}