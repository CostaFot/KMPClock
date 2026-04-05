package com.markedasduplicate.kmpclock.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kmpclock.composeapp.generated.resources.Res
import kmpclock.composeapp.generated.resources.tray_icon
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun clockIcon(): Painter = painterResource(Res.drawable.tray_icon)
