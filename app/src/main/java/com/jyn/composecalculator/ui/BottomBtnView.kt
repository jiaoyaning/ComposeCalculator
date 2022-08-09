package com.jyn.composecalculator.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jyn.composecalculator.BOTTOM_FRACTION
import com.jyn.composecalculator.DateViewModel
import com.jyn.composecalculator.R
import com.jyn.composecalculator.isPortrait

/**
 * 底部按钮
 * Created by jiaoyaning on 2022/8/6.
 */
private val numberColumns = listOf(
    listOf("C", "7", "4", "1", "00"),
    listOf("%", "8", "5", "2", "0"),
    listOf("D", "9", "6", "3", "."),
    listOf("÷", "×", "-", "+", "="),
)

@Preview(showBackground = true)
@Composable
fun BottomBtnView() {
    Row(
        Modifier
            .padding(bottom = 10.dp)
            .fillMaxHeight(BOTTOM_FRACTION)
    ) {
        if (!isPortrait) {
            Row( //数列
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            }
        }

        //竖屏基础按钮
        Row( //数列
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            numberColumns.forEach { numberColumn ->
                Column( //横排
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    numberColumn.forEach {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .weight(1f)
                                .then(if (isPortrait) Modifier.aspectRatio(1f) else Modifier)
                        ) {
                            ItemBtn(text = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemBtn(text: String) {
    val viewModel = viewModel<DateViewModel>()

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(1000.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = { textClick(viewModel, text) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (text == "D") {
                Icon(
                    modifier = Modifier.size(if (isPortrait) 30.dp else 15.dp),
                    painter = painterResource(R.drawable.ic_backspace),
                    contentDescription = ""
                )
                return@Box
            }
            Text(
                text = text,
                fontSize = if (isPortrait) 30.sp else 15.sp
            )
        }
    }
}

fun textClick(viewModel: DateViewModel, text: String) {
    when (text) {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "00",
        "÷", "×", "-", "+"
        -> viewModel.append(text)
        "D"
        -> viewModel.delete()
        "C"
        -> viewModel.clear()
        "="
        -> viewModel.calculate()
    }
}