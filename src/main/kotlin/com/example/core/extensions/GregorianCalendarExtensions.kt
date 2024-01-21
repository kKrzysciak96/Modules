package com.example.core.extensions

import java.util.*

fun GregorianCalendar.daysCount(): Int {
    return when (get(Calendar.MONTH)) {
        Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
        Calendar.FEBRUARY -> if (isLeapYear(get(Calendar.YEAR))) 29 else 28
        Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
        else -> 0
    }
}