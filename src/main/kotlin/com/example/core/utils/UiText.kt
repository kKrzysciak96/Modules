package com.example.core.utils


sealed interface UiText {
    data class StringResource(val stringResId: Int) : UiText
    data class DynamicString(val text: String) : UiText

    fun asString(): String {
        return when (this) {
            is DynamicString -> this.text
            else -> "No Text"
        }

    }
}