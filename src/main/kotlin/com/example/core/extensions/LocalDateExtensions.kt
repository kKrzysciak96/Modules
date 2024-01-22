package com.example.core.extensions

import java.sql.Date.valueOf
import java.time.LocalDate
import java.util.*

fun LocalDate.toDate(): Date {
    return valueOf(this)
}