package com.ptut.portfolio.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowWidthClass { Compact, Medium, Expanded }

fun windowWidthClassFor(maxWidth: Dp): WindowWidthClass = when {
    maxWidth < 600.dp -> WindowWidthClass.Compact
    maxWidth < 840.dp -> WindowWidthClass.Medium
    else -> WindowWidthClass.Expanded
}
