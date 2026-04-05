package com.markedasduplicate.kmpclock

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.markedasduplicate.kmpclock.data.PositionRepository
import com.markedasduplicate.kmpclock.di.appModule
import com.markedasduplicate.kmpclock.platform.WindowStyleHelper
import com.markedasduplicate.kmpclock.ui.App
import com.markedasduplicate.kmpclock.ui.clockIcon
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }

    val positionRepository = GlobalContext.get().get<PositionRepository>()
    val windowStyleHelper = GlobalContext.get().get<WindowStyleHelper>()

    application {
        var isVisible by remember { mutableStateOf(true) }

        val savedPosition = remember { positionRepository.loadPosition() }
        val windowState = rememberWindowState(
            width = 90.dp,
            height = 36.dp,
            position = savedPosition ?: WindowPosition(Alignment.BottomEnd),
        )

        LaunchedEffect(windowState.position) {
            val pos = windowState.position
            if (pos is WindowPosition.Absolute) {
                positionRepository.savePosition(pos)
            }
        }

        Tray(
            icon = clockIcon(),
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
                windowStyleHelper.hideFromTaskbar("KMPClock")
            }
            WindowDraggableArea {
                App()
            }
        }
    }
}
