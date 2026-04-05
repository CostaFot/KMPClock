package com.markedasduplicate.kmpclock

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import java.io.File

private interface User32 : Library {
    fun GetWindowLongA(hWnd: Pointer, nIndex: Int): Int
    fun SetWindowLongA(hWnd: Pointer, nIndex: Int, dwNewLong: Int): Int
    fun FindWindowA(lpClassName: String?, lpWindowName: String?): Pointer?

    companion object {
        val INSTANCE: User32 = Native.load("user32", User32::class.java)
        const val GWL_EXSTYLE = -20
        const val WS_EX_TOOLWINDOW = 0x00000080
        const val WS_EX_APPWINDOW = 0x00040000
    }
}

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

private object ClockIcon : Painter() {
    override val intrinsicSize = Size(16f, 16f)
    override fun DrawScope.onDraw() {
        drawCircle(color = Color.White)
        drawCircle(color = Color(0x99000000), radius = size.minDimension / 2 - 2f)
    }
}

fun main() = application {
    var isVisible by remember { mutableStateOf(true) }

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

    Tray(
        icon = ClockIcon,
        tooltip = "KMPClock",
        menu = {
            Item(if (isVisible) "Hide" else "Show", onClick = { isVisible = !isVisible })
            Separator()
            Item("Exit", onClick = ::exitApplication)
        },
    )

    Window(
        onCloseRequest = { isVisible = false },
        state = windowState,
        visible = isVisible,
        title = "KMPClock",
        undecorated = true,
        transparent = true,
        resizable = false,
        alwaysOnTop = true,
    ) {
        LaunchedEffect(Unit) {
            User32.INSTANCE.FindWindowA(null, "KMPClock")?.let { hwnd ->
                val exStyle = User32.INSTANCE.GetWindowLongA(hwnd, User32.GWL_EXSTYLE)
                User32.INSTANCE.SetWindowLongA(
                    hwnd, User32.GWL_EXSTYLE,
                    (exStyle or User32.WS_EX_TOOLWINDOW) and User32.WS_EX_APPWINDOW.inv(),
                )
            }
        }
        WindowDraggableArea {
            App()
        }
    }
}
