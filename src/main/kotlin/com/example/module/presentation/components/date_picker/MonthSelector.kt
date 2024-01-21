package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun MonthSelector(
    month: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandMonthList by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.clickable { expandMonthList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = monthName(month).uppercase())
        Spacer(Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)

        DropdownMenu(
            scrollState = scrollState,
            expanded = expandMonthList,
            onDismissRequest = { expandMonthList = false }
        ) {

            for (m in Calendar.JANUARY..Calendar.DECEMBER) {
                DropdownMenuItem(onClick = {
                    onValueChange(m)
                    expandMonthList = false
                }) {
                    Text(text = monthName(m).uppercase())
                }
            }
        }
    }
}

private fun monthName(month: Int): String {
    return when (month) {
        GregorianCalendar.JANUARY -> "JANUARY"
        GregorianCalendar.FEBRUARY -> "FEBRUARY"
        GregorianCalendar.MARCH -> "MARCH"
        GregorianCalendar.APRIL -> "APRIL"
        GregorianCalendar.MAY -> "MAY"
        GregorianCalendar.JUNE -> "JUNE"
        GregorianCalendar.JULY -> "JULY"
        GregorianCalendar.AUGUST -> "AUGUST"
        GregorianCalendar.SEPTEMBER -> "SEPTEMBER"
        GregorianCalendar.OCTOBER -> "OCTOBER"
        GregorianCalendar.NOVEMBER -> "NOVEMBER"
        GregorianCalendar.DECEMBER -> "DECEMBER"
        else -> "Undefined"
    }
}