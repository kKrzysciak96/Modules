package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Day(
    day: Int,
    selected: Boolean,
    onChangeValue: (Int) -> Unit
) {
    val clickable = day != 0

    Box(
        modifier = Modifier.size(48.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(50))
            .background(
                if (selected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }
            ).clickable(enabled = clickable) { onChangeValue(day) },
        contentAlignment = Alignment.Center
    ) {
        if (day != 0) {
            Text(
                text = day.toString(),
                color = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
    }
}