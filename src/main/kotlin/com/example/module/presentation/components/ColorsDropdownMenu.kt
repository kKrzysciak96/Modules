package com.example.module.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.module.presentation.utils.TextEditorColors


@Composable
fun ColorSelector(
    modifier: Modifier = Modifier,
    onColorClick: (Color) -> Unit,
    onColorButtonClick: () -> Unit,
    currentColor: Color = Color.Red,
    isColorDropdownMenuVisible: Boolean,
    onDismissRequest: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onColorButtonClick,
//            colors = IconButtonDefaults.iconButtonColors(contentColor = currentColor)
        ) {
            Icon(imageVector = Icons.Default.Circle, contentDescription = null)
        }
        ColorsDropdownMenu(
            modifier = Modifier.align(Alignment.BottomCenter),
            expanded = isColorDropdownMenuVisible,
            onClick = onColorClick,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun ColorsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        modifier = modifier
            .background(Color.White)
            .width(IntrinsicSize.Min),
    ) {
        TextEditorColors.values().forEach { textColor ->
            DropdownMenuItem(
                modifier = Modifier.width(50.dp),
                onClick = {
                    onClick(textColor.color)
                    onDismissRequest()
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = textColor.color
                    )
                })
        }
    }
}

