package com.jyn.composecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 底部按钮
 * Created by jiaoyaning on 2022/8/6.
 */
private val numberColumns = listOf(
    listOf("C", "7", "4", "1", "%"),
    listOf("÷", "8", "5", "2", "0"),
    listOf("x", "9", "6", "3", "."),
    listOf("D", "-", "+", "="),
)

@Composable
fun BottomBtnView() {
    Surface(
        Modifier
            .padding(bottom = 10.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxHeight(0.618f)
            .fillMaxWidth()
    ) {
        Row( //数列
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
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
                                .weight(if (it == "=") 2f else 1f)
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
    val modifier = if (text != "=") Modifier.aspectRatio(1f) else Modifier
    Button(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(1000.dp),
        onClick = { /*TODO*/ },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, fontSize = 30.sp)
        }
    }
}