package com.jyn.composecalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * 上层结果
 * Created by jiaoyaning on 2022/8/6.
 */

@Composable
fun TopResultView() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val topHeight = screenHeight * BOTTOM_FRACTION

    Surface(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .offset(y = -topHeight, x = 0.dp),
        shape = RoundedCornerShape(25.dp),
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text("TopResultView")
        }
    }
}