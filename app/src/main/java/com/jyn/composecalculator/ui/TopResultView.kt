package com.jyn.composecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jyn.composecalculator.Greeting

/**
 * 上层结果
 * Created by jiaoyaning on 2022/8/6.
 */

@Composable
fun TopResultView() {
    Box(Modifier.background(MaterialTheme.colorScheme.onError)) {
        Greeting("TopResultView")
    }
}