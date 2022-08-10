package com.jyn.composecalculator

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import com.udojava.evalex.Expression

data class Date(var input: String, var result: String)

class DateViewModel(application: Application) : AndroidViewModel(application) {
    var textBoxHeight = 0.dp
    var results = mutableListOf<Date>()
    var inputText = mutableStateOf("")
    var resultText = mutableStateOf("")
    var lastInputText = mutableStateOf("")

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
        if (lastInputText.value == inputText.value) {
            inputText.value = resultText.value
            resultText.value = ""
            return
        }

        val expr = inputText.value.replace('×', '*').replace('÷', '/').replace("%", "/100")
        val expressions = arrayOf('+', '-', '*', '/')
        if (expr.isEmpty()) return
        if (!expressions.any { expr.contains(it) }) {
            return
        }
        if (expressions.any { expr.endsWith(it) }) {
            resultText.value = ""
            return
        }
        try {
            val eval = Expression(expr).setPrecision(18).eval()
            resultText.value = eval.toString()
            lastInputText.value = inputText.value

            if (results.size > 10) results.removeFirst()
            results.add(0, Date(inputText.value, resultText.value))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(getApplication(), "计算错误！", Toast.LENGTH_SHORT).show()
        }
    }
}