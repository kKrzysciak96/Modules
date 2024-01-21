package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CalendarDaysHeader(vararg daysNames: String) {
    Row {
        for (dayName in daysNames) {
            DayName(dayName)
        }
    }
}

@Composable
private fun DayName(day: String) {
    Box(
        modifier = Modifier.size(48.dp)
            .padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            color = MaterialTheme.colors.primary
        )
    }
}