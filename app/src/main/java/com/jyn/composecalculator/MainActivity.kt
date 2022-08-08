package com.jyn.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.jyn.composecalculator.ui.BottomBtnView
import com.jyn.composecalculator.ui.TopResultView
import com.jyn.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.getLogConfig().configShowBorders(false)
        setContent {
            ComposeCalculatorTheme {
                val viewModel = viewModel<DateViewModel>()
                LogUtils.tag("viewModel").i("MainActivity viewModel : $viewModel")
                ContentView()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ContentView() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomBtnView()
            TopResultView()
        }
    }
}