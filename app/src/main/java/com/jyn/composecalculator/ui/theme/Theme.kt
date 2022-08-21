package com.jyn.composecalculator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.jyn.composecalculator.isDark


open class MyTheme(
    val textColor: Color,       //字体颜色
    val btnNumBg: Color,        //数字
    val btnComputeBg: Color,    // + - * /
    val btnEqualBg: Color,      // =号
    val btnClearBg: Color,      // C号
    val bottomBg:Color,         // 底部背景色
    val topBg: Color,           // 顶部背景色
    val topListBg: Color,       // 顶部列表背景色
)

private val MyLightColor = MyTheme(
    textColor = textColorLight,
    btnNumBg = btnNumBgLight,
    btnComputeBg = btnComputeBgLight,
    btnEqualBg = btnEqualBgLight,
    btnClearBg = btnClearBgLight,
    bottomBg = bottomBgLight,
    topBg = topBgLight,
    topListBg = topListBgLight,
)

private val MyDarkColor = MyTheme(
    textColor = textColorDark,
    btnNumBg = btnNumBgDark,
    btnComputeBg = btnComputeBgDark,
    btnEqualBg = btnEqualBgDark,
    btnClearBg = btnClearBgDark,
    bottomBg = bottomBgDark,
    topBg = topBgDark,
    topListBg = topListBgDark,
)

val myTheme: MyTheme
    get() = if (isDark) MyDarkColor else MyLightColor

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun ComposeCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    isDark = darkTheme
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = myTheme.topBg.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Immutable
object SecondaryRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        pressedAlpha = 0f,
        focusedAlpha = 0f,
        draggedAlpha = 0f,
        hoveredAlpha = 0f
    )
}