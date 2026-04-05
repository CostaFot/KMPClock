package com.markedasduplicate.kmpclock.ui

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

internal object ClockIcon : Painter() {
    override val intrinsicSize = Size(16f, 16f)
    override fun DrawScope.onDraw() {
        drawCircle(color = Color.White)
        drawCircle(color = Color(0x99000000), radius = size.minDimension / 2 - 2f)
    }
}
