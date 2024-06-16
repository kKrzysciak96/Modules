package com.example.module.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor


@Composable
fun ModuleNameTextRow(
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    label: String,
    text: String,
    isIconEnabled: Boolean,
    onValueChange: (String) -> Unit,
    onIconClick: () -> Unit
) {
    val richTextState = rememberRichTextState()

    LaunchedEffect(key1 = true) {
        richTextState.setText(text)
    }
    LaunchedEffect(key1 = richTextState.annotatedString, block = {
        onValueChange(richTextState.annotatedString.text)
    })

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:")

        if (isIconEnabled) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
                modifier = Modifier.clickable { onIconClick() })
        } else {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.clickable { onIconClick() })
        }
    }
    if (isIconEnabled) {
        RichTextEditor(
            modifier = Modifier,
            state = richTextState,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions.Default,
            textStyle = LocalTextStyle.current

        )
    } else {
        Text(text = text)
    }
}