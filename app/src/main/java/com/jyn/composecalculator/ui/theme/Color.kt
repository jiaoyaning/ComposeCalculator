package com.jyn.composecalculator.ui.theme

import android.animation.ArgbEvaluator
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.toArgb

val argbEvaluator by lazy { ArgbEvaluator() }
fun evaluator(process: Float, start: Color, end: Color) = Color(
    argbEvaluator.evaluate(
        process,
        start.toArgb(),
        end.toArgb()
    ) as Int
)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val textColorLight = Color(0xFF021C35)
val btnNumBgLight = Color(0xFFF8F9FC)
val btnComputeBgLight = Color(0xFFC4E7FF)
val btnEqualBgLight = Color(0xFFD1E3FD)
val btnClearBgLight = Color(0xFFC3EFD0)
val bottomBgLight = Color(0xFFFFFFFF)
val topBgLight = Color(0xFFEAEFFA)

val textColorDark = Color(0xFFC0E7FB)
val btnNumBgDark = Color(0xFF29292B)
val btnComputeBgDark = Color(0xFF004A78)
val btnEqualBgDark = Color(0xFF0742A0)
val btnClearBgDark = Color(0xFF105225)
val bottomBgDark = Color(0xFF1F1F1F)
val topBgDark = Color(0xFF38393C)
