package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePickerHeader(year: Int, month: Int, day: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = SimpleDateFormat("EEEE, d MMMM yyyy").format(
                GregorianCalendar(
                    year, month, day
                ).time
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
    }
}