package com.markedasduplicate.kmpclock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun App() {
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    var time by remember { mutableStateOf(LocalTime.now().format(formatter)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            time = LocalTime.now().format(formatter)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = time,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
