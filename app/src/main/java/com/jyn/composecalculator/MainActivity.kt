package com.jyn.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.apkfuns.logutils.LogUtils
import com.jyn.composecalculator.ui.ContentView
import com.jyn.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.getLogConfig().configShowBorders(false)
        setContent {
            ComposeCalculatorTheme {
                ContentView()
            }
        }
    }
}