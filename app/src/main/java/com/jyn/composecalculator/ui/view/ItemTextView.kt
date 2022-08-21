package com.jyn.composecalculator.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jyn.composecalculator.DateViewModel
import com.jyn.composecalculator.isPortrait
import com.jyn.composecalculator.ui.theme.myTheme

@Composable
fun InputText(process: Float) {
    val viewModel = viewModel<DateViewModel>()

    val inputScrollState = rememberScrollState()
    val resultScrollState = rememberScrollState()
    val inputTextWidth = remember { mutableStateOf(0) }
    val resultTextWidth = remember { mutableStateOf(0) }

    LaunchedEffect(inputTextWidth.value) {
        inputScrollState.scrollTo(inputTextWidth.value) //自动滚动到最后一个
    }
    LaunchedEffect(inputTextWidth.value) {
        resultScrollState.scrollTo(resultTextWidth.value) //自动滚动到最后一个
    }

    Box {
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(myTheme.topBg)
//                .background(evaluator(1- process, myTheme.bottomBg, myTheme.topBg))
                .height(viewModel.textBoxHeight * 1f)
                .alpha(1f)
                .padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(inputScrollState)
                        .onSizeChanged { inputTextWidth.value = it.width },
                    text = viewModel.inputText.value,
                    color = myTheme.textColor,
                    maxLines = 1,
                    fontSize = if (isPortrait) 50.sp else 28.sp,
                    textAlign = TextAlign.End,
                )
                CursorView(if (isPortrait) 50.dp else 25.dp)
            }

            Text(
                text = viewModel.resultText.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
                    .horizontalScroll(resultScrollState)
                    .onSizeChanged { resultTextWidth.value = it.width },
                maxLines = 1,
                fontSize = if (isPortrait) 30.sp else 20.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp)
                .alpha(process),
            text = "当前表达式",
            color = myTheme.textColor,
            fontSize = if (isPortrait) 20.sp else 15.sp,
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ItemText(input: String, result: String) {
    Column(
        modifier = Modifier.height(120.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = input,
            fontSize = if (isPortrait) 33.sp else 25.sp,
            maxLines = 1,
            textAlign = TextAlign.End,
            color = myTheme.textColor,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = result,
            color = Color.Gray,
            fontSize = if (isPortrait) 26.sp else 23.sp,
            maxLines = 1,
            textAlign = TextAlign.End,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemTextPre() {
    ItemText("123456+1231231", "67474774747")
}