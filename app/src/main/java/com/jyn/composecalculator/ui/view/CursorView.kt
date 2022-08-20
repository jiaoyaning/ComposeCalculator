package com.jyn.composecalculator.ui.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jyn.composecalculator.isDark

/**
 * 光标
 */
@Composable
fun CursorView(height: Dp) {
    val infiniteTransition = rememberInfiniteTransition()
    val float = infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Spacer(
        modifier = Modifier
            .background(
                if (float.value > 0.5)
                    if (isDark) Color(0xFFAEC9F9) else Color(0xFF175BC4)
                else
                    Color.Transparent
            )
            .width(3.dp)
            .height(height)
    )
}