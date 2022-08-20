package com.jyn.composecalculator

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jyn.composecalculator.ui.BottomBtnView
import com.jyn.composecalculator.ui.TopResultView
import com.jyn.composecalculator.ui.theme.ComposeCalculatorTheme
import com.jyn.composecalculator.ui.theme.myTheme

const val BOTTOM_FRACTION = 0.67f
var isPortrait = false //横竖屏
var isDark = false //暗黑模式

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        LogUtils.getLogConfig().configShowBorders(false)
        setContent {
            ComposeCalculatorTheme {
                //是否是竖屏
                isPortrait = LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT
                val viewModel = viewModel<DateViewModel>()
                LogUtils.tag("viewModel").i("MainActivity viewModel : $viewModel")

                ContentView()

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = myTheme.topBg,
                        darkIcons = !useDarkIcons
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ContentView() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(myTheme.bottomBg),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomBtnView()
            TopResultView()
        }
    }
}