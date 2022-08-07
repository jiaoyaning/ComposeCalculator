package com.jyn.composecalculator.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apkfuns.logutils.LogUtils

/**
 * 上层结果
 * Created by jiaoyaning on 2022/8/6.
 */
enum class Status {
    MIN, MAX
}

@Preview(showBackground = true)
@Composable
fun TopResultView() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val topHeight = screenHeight * BOTTOM_FRACTION + 10.dp

    Surface(
        modifier = Modifier
            .width(screenWidth)
            .height(screenHeight)
            .offset(y = -topHeight),
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text("TopResultView", fontSize = 22.sp)
        }
    }
}