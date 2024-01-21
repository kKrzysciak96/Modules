package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.*

@Composable
fun DatePickerButtonsRow(
    year: Int,
    month: Int,
    day: Int,
    onDismissRequest: () -> Unit,
    onDateSelect: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { onDismissRequest() }
        ) {
            Text(
                text = "Anuluj"
            )
        }

        TextButton(
            onClick = {
                onDateSelect(GregorianCalendar(year, month, day).time)
            }
        ) {
            Text(
                text = "Ok"
            )
        }
    }
}