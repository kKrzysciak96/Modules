package com.example.module.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@Composable
fun CustomModuleDialogRow(
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholderText: String,
    singleLine: Boolean = false,
    richTextState: RichTextState
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.width(100.dp)
        )
        RichTextEditor(
            modifier = Modifier
                .weight(1f),
            state = richTextState,
            placeholder = { Text(text = placeholderText) },
            singleLine = singleLine,
            keyboardOptions = keyboardOptions
        )
    }
}