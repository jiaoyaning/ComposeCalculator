package com.jyn.composecalculator.ui.view

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jyn.composecalculator.DateViewModel


@Composable
fun InputText(input: String) {
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

    Column(
        modifier = Modifier.height(viewModel.textBoxHeight),
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(inputScrollState)
                    .onSizeChanged { inputTextWidth.value = it.width },
                text = input,
                maxLines = 1,
                fontSize = 50.sp,
                textAlign = TextAlign.End,
            )
            CursorView()
        }

        Text(
            text = viewModel.resultText.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 5.dp)
                .horizontalScroll(resultScrollState)
                .onSizeChanged { resultTextWidth.value = it.width },
            maxLines = 1,
            fontSize = 30.sp,
            color = Color.Gray,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun ItemText(input: String, result: String) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Text(modifier = Modifier.fillMaxWidth(), text = input, textAlign = TextAlign.End)
    }
}