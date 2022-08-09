package com.jyn.composecalculator.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Stroke
import com.apkfuns.logutils.LogUtils
import com.jyn.composecalculator.ui.theme.black1

/**
 * 取值1..100
 */
@Composable
fun SlideIndicator(process: Float) {
    var offsetProcess = process
    if (offsetProcess < 0) offsetProcess *= 10
    LogUtils.tag("SlideIndicator").i("process: $offsetProcess")
    val width = 30.dp
    val height = 4.dp
    val maxOffset = 3.dp

    Canvas(
        Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val yOffset = maxOffset.toPx() * offsetProcess / 100

        val half = width.toPx() / 2
        val leftStart = centerX - half
        val rightEnd = centerX + half

        val path = Path().apply {
            moveTo(leftStart, centerY + yOffset)
            lineTo(centerX, centerY - yOffset)
            lineTo(rightEnd, centerY + yOffset)
        }
        drawPath(
            path = path,
            color = black1,
            style = Stroke(
                width = height.toPx(),
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 100, heightDp = 40)
@Composable
fun SlideIndicatorPV1() {
    SlideIndicator(process = 0f)
}

@Preview(showBackground = true, widthDp = 100, heightDp = 40)
@Composable
fun SlideIndicatorPV2() {
    SlideIndicator(process = 100f)
}

@Preview(showBackground = true, widthDp = 100, heightDp = 40)
@Composable
fun SlideIndicatorPV3() {
    SlideIndicator(process = 50f)
}