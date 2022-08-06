package com.jyn.composecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jyn.composecalculator.Greeting

/**
 * 底部按钮
 * Created by jiaoyaning on 2022/8/6.
 */
private val numberColumns = listOf(
    listOf("7", "4", "1", "0"),
    listOf("8", "5", "2", "."),
    listOf("9", "6", "3", "=")
)

@Composable
fun BottomBtnView() {
    Surface(
        Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            numberColumns.forEach { numberColumn ->
                Column(modifier = Modifier.weight(1f)) {
                    numberColumn.forEach { ItemBtn(it) }
                }
            }
        }
    }
}

@Composable
fun ItemBtn(text: String) {
    IconButton(onClick = { /*TODO*/ }) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text)
        }
    }
}