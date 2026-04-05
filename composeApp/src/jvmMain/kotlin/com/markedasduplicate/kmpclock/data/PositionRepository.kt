package com.markedasduplicate.kmpclock.data

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
private data class PositionConfig(val x: Float, val y: Float)

class PositionRepository {

    private val configFile = File(System.getProperty("user.home"), ".kmpclock.json")

    fun loadPosition(): WindowPosition? {
        if (!configFile.exists()) return null
        return try {
            val config = Json.decodeFromString<PositionConfig>(configFile.readText())
            WindowPosition(config.x.dp, config.y.dp)
        } catch (e: Exception) {
            null
        }
    }

    fun savePosition(pos: WindowPosition.Absolute) {
        val config = PositionConfig(pos.x.value, pos.y.value)
        configFile.writeText(Json.encodeToString(config))
    }
}
