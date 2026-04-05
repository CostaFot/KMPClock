package com.markedasduplicate.kmpclock

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.io.File

private val configFile = File(System.getProperty("user.home"), ".kmpclock.json")

private fun loadPosition(): WindowPosition? {
    if (!configFile.exists()) return null
    return try {
        val json = configFile.readText()
        val x = Regex(""""x"\s*:\s*([\d.]+)""").find(json)?.groupValues?.get(1)?.toFloatOrNull()
        val y = Regex(""""y"\s*:\s*([\d.]+)""").find(json)?.groupValues?.get(1)?.toFloatOrNull()
        if (x != null && y != null) WindowPosition(x.dp, y.dp) else null
    } catch (e: Exception) {
        null
    }
}

private fun savePosition(pos: WindowPosition.Absolute) {
    configFile.writeText("""{"x":${pos.x.value},"y":${pos.y.value}}""")
}

fun main() = application {
    val savedPosition = remember { loadPosition() }
    val windowState = rememberWindowState(
        width = 90.dp,
        height = 36.dp,
        position = savedPosition ?: WindowPosition(Alignment.BottomEnd),
    )

    LaunchedEffect(windowState.position) {
        val pos = windowState.position
        if (pos is WindowPosition.Absolute) {
            savePosition(pos)
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "KMPClock",
        undecorated = true,
        transparent = true,
        resizable = false,
        alwaysOnTop = true,
    ) {
        WindowDraggableArea {
            App()
        }
    }
}
