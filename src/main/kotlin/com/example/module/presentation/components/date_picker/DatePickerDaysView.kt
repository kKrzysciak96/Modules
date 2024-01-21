package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.core.extensions.daysCount
import java.util.*


@Composable
fun DatePickerDaysView(year: Int, month: Int, day: MutableState<Int>, modifier: Modifier = Modifier) {

    val startDay = GregorianCalendar(year, month, 1)
        .apply {
            firstDayOfWeek = Calendar.SUNDAY
        }.get(Calendar.DAY_OF_WEEK)

    var render = false
    var dayCounter = 1
    val maxDay = GregorianCalendar(year, month, 1).daysCount()

    if (day.value > maxDay) {
        day.value = maxDay
    }

    for (i in 1..6) {
        Row(modifier = modifier) {
            for (j in 1..7) {
                if (j == startDay) {
                    render = true
                }
                if (!render || dayCounter > maxDay) {
                    Day(0, false) { }
                } else {
                    Day(
                        day = dayCounter,
                        selected = (day.value == dayCounter),
                        onChangeValue = { day.value = it }
                    )
                    dayCounter++
                }
            }
        }
    }
}