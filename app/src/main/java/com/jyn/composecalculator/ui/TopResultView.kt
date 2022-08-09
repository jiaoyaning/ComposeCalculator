package com.jyn.composecalculator.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableDefaults.resistanceConfig
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.jyn.composecalculator.BOTTOM_FRACTION
import com.jyn.composecalculator.DateViewModel
import com.jyn.composecalculator.ui.view.CursorView
import com.jyn.composecalculator.ui.view.InputText
import com.jyn.composecalculator.ui.view.SlideIndicator

/**
 * 上层结果
 * Created by jiaoyaning on 2022/8/6.
 */

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun TopResultView() {
    val viewModel = viewModel<DateViewModel>()
    LogUtils.tag("viewModel").i("TopResultView viewModel : $viewModel")

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    //偏移量
    val topHeight = screenHeight * BOTTOM_FRACTION + 10.dp
    val textBoxHeight = screenHeight - topHeight
    viewModel.textBoxHeight = textBoxHeight

    //记录是否是最小值
    val state = rememberSwipeableState(true)
    val blockSizePx = with(LocalDensity.current) { -topHeight.toPx() }
    val anchors = mapOf(0f to false, blockSizePx to true)
    Surface(
        modifier = Modifier
            .width(screenWidth)
            .height(screenHeight)
            .offset { IntOffset(0, state.offset.value.toInt()) }
            .swipeable(
                orientation = Orientation.Vertical,
                state = state,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.2f) },
                resistance = resistanceConfig(
                    anchors.keys,
                    10.dp.value,
                    0f
                ),
                velocityThreshold = 60.dp
            ),
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
        tonalElevation = 3.dp,
        shadowElevation = 3.dp
    ) {
        TextBox(process = 100 - (state.offset.value / blockSizePx * 100))
    }
}

@Composable
fun TextBox(process: Float) {
    val viewModel = viewModel<DateViewModel>()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        InputText(viewModel.inputText.value)

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SlideIndicator(process)
        }
    }
}