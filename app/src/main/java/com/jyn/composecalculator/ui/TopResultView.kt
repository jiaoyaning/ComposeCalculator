package com.jyn.composecalculator.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SwipeableDefaults.resistanceConfig
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.jyn.composecalculator.BOTTOM_FRACTION
import com.jyn.composecalculator.DateViewModel
import com.jyn.composecalculator.ui.theme.myTheme
import com.jyn.composecalculator.ui.view.InputText
import com.jyn.composecalculator.ui.view.ItemText
import com.jyn.composecalculator.ui.view.SlideIndicator
import kotlinx.coroutines.launch
import kotlin.math.abs

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

    val coroutineScope = rememberCoroutineScope()

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
                    5.dp.value,
                    0f
                ),
                velocityThreshold = 60.dp
            ),
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
        tonalElevation = 3.dp,
        shadowElevation = 3.dp
    ) {
        TextBox(process = state.offset.value / blockSizePx)
    }

    val callback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!state.currentValue) {
                    coroutineScope.launch {
                        state.animateTo(true, SwipeableDefaults.AnimationSpec)
                    }
                }
            }
        }
    }
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(key1 = Unit, effect = {
        dispatcher?.addCallback(callback)
        onDispose {
            callback.remove()
        }
    })
}

/**
 * 计算布局 & 历史列表
 */
@Composable
fun TextBox(process: Float) {
    val viewModel = viewModel<DateViewModel>()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            reverseLayout = true,
            userScrollEnabled = true,
        ) {
            itemsIndexed(viewModel.results) { index, item ->
                Box(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        viewModel.inputText.value = item.input
                        viewModel.resultText.value = item.result
                    }) {
                    ItemText(input = item.input, result = item.result)
                    if (index < viewModel.results.size - 1) {
                        Divider()
                    }
                }
            }
        }

        InputText(process)

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp * abs(1 - process))
                .fillMaxWidth()
        ) {
            SlideIndicator(process)
        }
    }
}

