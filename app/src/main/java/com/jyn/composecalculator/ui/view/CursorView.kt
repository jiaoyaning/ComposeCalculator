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
import androidx.compose.ui.unit.dp

/**
 * 光标
 */
@Preview(showBackground = true)
@Composable
fun CursorView() {
    val infiniteTransition = rememberInfiniteTransition()
    val float = infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Spacer(
        modifier = Modifier
            .background(if (float.value > 0.5) Color.Red else Color.Transparent)
            .width(2.dp)
            .height(50.dp)
    )
}