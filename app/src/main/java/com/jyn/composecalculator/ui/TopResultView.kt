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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.jyn.composecalculator.ui.theme.evaluator
import com.jyn.composecalculator.ui.theme.myTheme
import com.jyn.composecalculator.ui.view.InputText
import com.jyn.composecalculator.ui.view.ItemText
import com.jyn.composecalculator.ui.view.SlideIndicator
import kotlinx.coroutines.launch

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

    val process = 1 - state.offset.value / blockSizePx

    Surface(
        modifier = Modifier
            .width(screenWidth)
            .fillMaxHeight()
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
        color = myTheme.topBg,
        shape = RoundedCornerShape(
            bottomStart = 25.dp * (1 - process),
            bottomEnd = 25.dp * (1 - process)
        ),
        tonalElevation = 3.dp,
        shadowElevation = 3.dp
    ) {
        TextBox(process, onClick = {
            coroutineScope.launch {
                state.animateTo(true, SwipeableDefaults.AnimationSpec)
            }
        })
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
fun TextBox(process: Float, onClick: () -> Unit) {
    val viewModel = viewModel<DateViewModel>()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {
        TopAppBar(
            modifier = Modifier
                .padding(top = 30.dp)
                .height(50.dp),
            title = { Text("历史记录", color = myTheme.textColor) },
            navigationIcon = {
                IconButton(onClick) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        null,
                        tint = myTheme.textColor
                    )
                }
            },
            backgroundColor = myTheme.topBg
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(myTheme.topListBg)
                .padding(start = 10.dp, end = 10.dp),
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

        Surface(
            modifier = Modifier.background(myTheme.topListBg),
            color = myTheme.topListBg,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            tonalElevation = 4.dp * (process),
            shadowElevation = 4.dp * (process),
        ) {
            InputText(process)
        }

        Box(
            modifier = Modifier
                .background(myTheme.topBg)
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            SlideIndicator(process)
        }
    }
}

