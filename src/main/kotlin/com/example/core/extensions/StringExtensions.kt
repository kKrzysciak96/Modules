package com.example.core.extensions

fun String.isDigitsOnly(): Boolean {

    var isDigitOnly = true

    forEach { if (it !in "0123456789") isDigitOnly = false }

    return isDigitOnly
}