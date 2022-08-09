package com.jyn.composecalculator

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import com.udojava.evalex.Expression

class DateViewModel(application: Application) : AndroidViewModel(application) {
    var textBoxHeight = 0.dp
    var inputText = mutableStateOf("")
    var resultText = mutableStateOf("")

    fun append(text: String) {
        inputText.value = inputText.value + text
    }

    fun delete() {
        if (inputText.value.isEmpty()) return
        inputText.value = inputText.value.substring(0, inputText.value.length - 1)
    }

    fun clear() {
        inputText.value = ""
        resultText.value = ""
    }

    fun calculate() {
        val expr = inputText.value.replace('×', '*').replace('÷', '/')
        val expressions = arrayOf('+', '-', '*', '/')
        if (expr.isEmpty()) return
        if (!expressions.any { expr.contains(it) }) {
            return
        }
        if (expressions.any { expr.endsWith(it) }) {
            resultText.value = ""
            return
        }
        val eval = Expression(expr).eval()

        resultText.value = eval.toString()
    }
}