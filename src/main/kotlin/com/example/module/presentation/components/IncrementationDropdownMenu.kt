package com.example.module.presentation.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun IncrementationDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (Int) -> Unit,
    isResetAvailable: Boolean,
    modifier: Modifier = Modifier,
    onResetClick: () -> Unit = {},
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        modifier = modifier,
    ) {
        if (isResetAvailable) {
            DropdownMenuItem(onClick = {
                onResetClick()
            }, content = {
                Text(text = "Reset")
            })
        }
        (0..80).forEach { number ->
            DropdownMenuItem(onClick = {
                onClick(number)
            }, content = {
                Text(text = number.toString())
            })
        }
    }
}