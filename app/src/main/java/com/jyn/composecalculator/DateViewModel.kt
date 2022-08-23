package com.jyn.composecalculator

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import com.udojava.evalex.Expression
import java.text.SimpleDateFormat
import java.util.*

data class Date(var time: String, var input: String, var result: String)

class DateViewModel(application: Application) : AndroidViewModel(application) {
    var textBoxHeight = 0.dp
    var results = mutableStateListOf<Date>().apply {
        add(Date(getNow(), "24+32", "56"))
        add(Date(getNow(), "32+24", "56"))
        add(Date(getNow(), "32+24-6", "50"))
        add(Date(getNow(), "63+58", "121"))
        add(Date(getNow(), "396÷3", "132"))
        add(Date(getNow(), "123×3", "369"))
        add(Date(getNow(), "640×30", "19200"))
    }
    var inputText = mutableStateOf("")
    var resultText = mutableStateOf("")
    var lastInputText = mutableStateOf("")

    fun appendNum(text: String) {
        val replace = text.replace("sin", "sin(")
            .replace("cos", "cos(")
            .replace("tan", "tan(")
            .replace("log", "log(")
        inputText.value = inputText.value + replace
    }

    fun appendCompute(text: String) {
        if (!standards()) return
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

        val expr = inputText.value
            .replace('×', '*')
            .replace('÷', '/')
            .replace("%", "/100")
        val expressions =
            arrayOf("+", "-", "*", "/", "sin", "cos", "tan", "log", "^", "√", "!", "π")
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
            results.add(0, Date(getNow(), inputText.value, resultText.value))

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(getApplication(), "计算错误！", Toast.LENGTH_SHORT).show()
        }
    }

    private fun standards(): Boolean {
        val doesExist = inputText.value.last().toString() in arrayOf("÷", "×", "-", "+", ".")
        return !doesExist
    }
}

fun getNow(): String {
    return if (android.os.Build.VERSION.SDK_INT >= 24) {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Date())
    } else {
        val tms = Calendar.getInstance()
        tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH)
            .toString() + "-" + tms.get(Calendar.DAY_OF_MONTH)
            .toString() + " " + tms.get(Calendar.HOUR_OF_DAY)
            .toString() + ":" + tms.get(Calendar.MINUTE).toString() + ":" + tms.get(Calendar.SECOND)
    }
}