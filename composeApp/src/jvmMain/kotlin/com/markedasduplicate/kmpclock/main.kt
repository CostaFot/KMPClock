package com.markedasduplicate.kmpclock

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMPClock",
        undecorated = true,
        transparent = true,
        state = WindowState(
            width = 220.dp,
            height = 90.dp,
            position = WindowPosition(Alignment.BottomEnd),
        ),
    ) {
        WindowDraggableArea {
            App()
        }
    }
}
