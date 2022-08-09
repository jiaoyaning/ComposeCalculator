package com.jyn.composecalculator.ui.util

import com.jyn.composecalculator.DateViewModel

fun textClick(viewModel: DateViewModel, text: String) {
    when (text) {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "00",
        "÷", "×", "-", "+"
        -> viewModel.append(text)
        "D"
        -> viewModel.delete()
        "C"
        -> viewModel.clear()
        "="
        -> viewModel.calculate()
    }
}