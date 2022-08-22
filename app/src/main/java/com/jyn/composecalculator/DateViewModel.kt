package com.jyn.composecalculator

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import com.udojava.evalex.Expression
import java.text.SimpleDateFormat
import java.util.*

data class Date(var time: String, var input: String, var result: String)

class DateViewModel(application: Application) : AndroidViewModel(application) {
    var textBoxHeight = 0.dp
    var results = mutableListOf<Date>().apply {
        add(Date(getNow(), "1+1", "2"))
        add(Date(getNow(), "211111+1", "21111112"))
        add(Date(getNow(), "311111+1", "31111112"))
        add(Date(getNow(), "411111+1", "41111112"))
        add(Date(getNow(), "511111+1", "51111112"))
        add(Date(getNow(), "611111+1", "61111112"))
        add(Date(getNow(), "711111+1", "71111112"))
    }
    var inputText = mutableStateOf("")
    var resultText = mutableStateOf("")
    var lastInputText = mutableStateOf("")

    fun appendNum(text: String) {
        inputText.value = inputText.value + text
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