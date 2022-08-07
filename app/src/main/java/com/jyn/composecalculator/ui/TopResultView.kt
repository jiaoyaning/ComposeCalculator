package com.jyn.composecalculator.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 上层结果
 * Created by jiaoyaning on 2022/8/6.
 */
enum class Status {
    MIN, MAX
}

@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Preview(showBackground = true)
@Composable
fun TopResultView() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    //偏移量
    val topHeight = screenHeight * BOTTOM_FRACTION + 10.dp

    //记录是否是最小值
    val state = rememberSwipeableState(true, confirmStateChange = {
        LogUtils.tag("rememberSwipeableState").i("confirmStateChange: $it")
        true
    })
    val blockSizePx = with(LocalDensity.current) { -topHeight.toPx() }
    Surface(
        modifier = Modifier
            .width(screenWidth)
            .height(screenHeight)
            .offset { IntOffset(0, state.offset.value.toInt()) }
            .swipeable(
                orientation = Orientation.Vertical,
                state = state,
                anchors = mapOf(0f to false, blockSizePx to true),
                thresholds = { _, _ -> FractionalThreshold(0.2f) },
            ),
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text("TopResultView", fontSize = 22.sp)
        }
    }
}