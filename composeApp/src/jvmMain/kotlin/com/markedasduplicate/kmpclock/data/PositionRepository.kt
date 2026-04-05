package com.markedasduplicate.kmpclock.data

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import java.io.File

class PositionRepository {

    private val configFile = File(System.getProperty("user.home"), ".kmpclock.json")

    fun loadPosition(): WindowPosition? {
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

    fun savePosition(pos: WindowPosition.Absolute) {
        configFile.writeText("""{"x":${pos.x.value},"y":${pos.y.value}}""")
    }
}
