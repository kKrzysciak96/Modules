package com.example.module.presentation.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
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
            }, text = {
                Text(text = "Reset")
            })
        }
        (0..80).forEach { number ->
            DropdownMenuItem(onClick = {
                onClick(number)
            }, text = {
                Text(text = number.toString())
            })
        }
    }
}