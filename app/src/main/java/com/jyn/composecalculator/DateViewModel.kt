package com.jyn.composecalculator

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel

class DateViewModel(application: Application) : AndroidViewModel(application) {
    var textBoxHeight = 0.dp
    var inputText = mutableStateOf("")

    fun append(text: String) {
        inputText.value = inputText.value + text
    }
}