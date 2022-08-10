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
    var results = mutableListOf<Date>().apply {
        add(Date("111111+1","11111112"))
        add(Date("211111+1","21111112"))
        add(Date("311111+1","31111112"))
        add(Date("411111+1","41111112"))
        add(Date("511111+1","51111112"))
        add(Date("611111+1","61111112"))
        add(Date("711111+1","71111112"))
    }
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
            resultText.value = eval.toPlainString()
            lastInputText.value = inputText.value

            if (results.size > 10) results.removeFirst()
            results.add(0, Date(inputText.value, resultText.value))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(getApplication(), "计算错误！", Toast.LENGTH_SHORT).show()
        }
    }
}