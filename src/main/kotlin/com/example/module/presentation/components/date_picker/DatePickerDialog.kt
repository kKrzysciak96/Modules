package com.example.module.presentation.components.date_picker

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*


@Preview
@Composable
fun DatePickerDialog(
    initDate: Date = Date(),
    onDateSelect: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    minYear: Int = GregorianCalendar().get(Calendar.YEAR) - 5,
    maxYear: Int = GregorianCalendar().get(Calendar.YEAR) + 5
) {
    val calendar = GregorianCalendar().apply {
        time = initDate
    }

    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var month by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val day = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    EmptyCustomDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePickerHeader(
                year = year,
                month = month,
                day = day.value,
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier.width(IntrinsicSize.Min),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    MonthSelector(
                        month = month,
                        onValueChange = { month = it }
                    )
                    YearSelector(
                        year = year,
                        onValueChange = { year = it },
                        minYear = minYear,
                        maxYear = maxYear,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                CalendarDaysHeader("Mo", "Tu", "We", "Th", "Ft", "Sa", "Su")
                Divider(Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colors.primary))
                DatePickerDaysView(
                    year = year,
                    month = month,
                    day = day
                )
                DatePickerButtonsRow(
                    year = year,
                    month = month,
                    day = day.value,
                    onDismissRequest = onDismissRequest,
                    onDateSelect = onDateSelect,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

