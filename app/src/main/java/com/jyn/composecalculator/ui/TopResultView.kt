package com.jyn.composecalculator.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SwipeableDefaults.resistanceConfig
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.jyn.composecalculator.BOTTOM_FRACTION
import com.jyn.composecalculator.DateViewModel
import com.jyn.composecalculator.isPortrait
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

    var process = 1f

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
            bottomStart = 25.dp * process,
            bottomEnd = 25.dp * process
        ),
        tonalElevation = 3.dp,
        shadowElevation = 3.dp
    ) {
        process = state.offset.value / blockSizePx
        Row() {
            Box(modifier = Modifier.weight(1f)) {
                TextBox(1 - process) {
                    coroutineScope.launch {
                        state.animateTo(!state.targetValue, SwipeableDefaults.AnimationSpec)
                    }
                }
            }
        }
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
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TextBox(process: Float, onClick: () -> Unit) {
    val viewModel = viewModel<DateViewModel>()
    val openDialog = remember { mutableStateOf(-1) }
    if (openDialog.value != -1) {
        AlertDialog(
            backgroundColor = myTheme.topBg,
            onDismissRequest = { openDialog.value = -1 },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.Red
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "是否决定删除该记录",
                        color = myTheme.textColor,
                        style = MaterialTheme.typography.h6
                    )
                }
            }, confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.results.removeAt(openDialog.value)
                        openDialog.value = -1
                    },
                ) { Text("确认", fontWeight = FontWeight.W700, color = myTheme.textColor) }
            }, dismissButton = {
                TextButton(onClick = { openDialog.value = -1 }) {
                    Text("取消", fontWeight = FontWeight.W700, color = myTheme.textColor)
                }
            })
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {

        Surface(
            modifier = Modifier
                .background(myTheme.topListBg)
                .padding(top = 25.dp, bottom = 10.dp),
            color = myTheme.topBg,
            shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
            tonalElevation = 4.dp * (process),
            shadowElevation = 4.dp * (process),
        ) {
            Box {
                InputText(process)

                //TitleBar
                if (isPortrait) {
                    TopAppBar(
                        modifier = Modifier.height(40.dp),
                        title = { Text("当前表达式", color = myTheme.textColor) },
                        navigationIcon = {
                            IconButton(onClick) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    null,
                                    tint = myTheme.textColor
                                )
                            }
                        },
                        backgroundColor = myTheme.topBg,
                        elevation = 0.dp
                    )
                }
            }
        }

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
                    modifier = Modifier.combinedClickable(
                        onClick = {
                            viewModel.inputText.value = item.input
                            viewModel.resultText.value = item.result
                        },
                        onLongClick = { openDialog.value = index })
                ) {
                    ItemText(input = item.input, result = item.result)
                }
            }
        }

        Box(
            modifier = Modifier
                .height(viewModel.textBoxHeight * (1 - process))
                .background(evaluator(1 - process, myTheme.topListBg, myTheme.topBg)),
        ) {
            InputText(process)
        }

        Box(
            modifier = Modifier
                .clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                .background(evaluator(1 - process, myTheme.topListBg, myTheme.topBg))
                .padding(top = 10.dp, bottom = 10.dp + 15.dp * process)
                .fillMaxWidth()
        ) {
            SlideIndicator(process)
        }
    }
}

