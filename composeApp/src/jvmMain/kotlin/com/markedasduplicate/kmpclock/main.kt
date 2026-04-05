package com.markedasduplicate.kmpclock

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMPClock",
        undecorated = true,
    ) {
        App()
    }
}