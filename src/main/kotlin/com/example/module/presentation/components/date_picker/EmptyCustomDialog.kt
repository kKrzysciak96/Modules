package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState

@Composable
fun EmptyCustomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit
) {
    val state: DialogState = rememberDialogState(width = 600.dp, height = 600.dp)
    DialogWindow(
        onCloseRequest = onDismissRequest,
        state = state,
        content = {
            DialogContent(
                modifier = modifier.fillMaxWidth(),
                content = content,
                shape = shape,
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        })
}

@Composable
internal fun DialogContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        content()
    }
}