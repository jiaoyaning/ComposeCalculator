package com.jyn.composecalculator

import android.app.Application
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel

class DateViewModel(application: Application) : AndroidViewModel(application) {
    var inputText = TextFieldValue(text = "")
    var outputText = TextFieldValue(text = "")
}