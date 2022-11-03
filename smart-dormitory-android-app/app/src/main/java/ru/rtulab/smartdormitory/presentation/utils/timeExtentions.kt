/*
package ru.rtulab.smartdormitory.presentation.utils

import java.time.Instant


import android.content.Context
import android.util.Log
import kotlinx.datetime.*
import kotlinx.datetime.Instant.Companion.fromEpochMilliseconds
import kotlinx.datetime.TimeZone
import ru.rtulab.smartdormitory.R
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val timeZone = TimeZone.of("UTC+3")

fun Instant.plus(value: Long, unit: DateTimeUnit) = plus(value, unit, timeZone)

fun Instant.minus(value: Long, unit: DateTimeUnit) = plus(-value, unit)

fun Instant.toMoscowDateTime() = toLocalDateTime(timeZone)

fun LocalDate.toUiString() = run {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    "$day.$month.$year"
}

fun Long.toMoscowDateTime() = Instant.fromEpochMilliseconds(this).toMoscowDateTime()

fun Long.toClientDate() = toMoscowDateTime().run {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    "$day.$month.$year"
}

fun String.fromIso8601(
    context: Context,
    dateTimeDelimiter: String = " ${context.resources.getString(R.string.at)}"
) = try {
    // ITLab uses both normalized and non-normalized
    // ISO8601 strings. This is a workaround to always
    // parse normalized strings
    (if (this.contains("Z")) this else this + "Z")
        .fromIso8601ToInstant().run {
            val day = dayOfMonth.toString().padStart(2, '0')
            val month = monthNumber.toString().padStart(2, '0')
            val hour = hour.toString().padStart(2, '0')
            val minute = minute.toString().padStart(2, '0')
            "$day.$month.$year$dateTimeDelimiter $hour:$minute"
        }
} catch (e: DateTimeException) {
    e.printStackTrace()
    Log.e("DateTime", "Unable to parse ${if (this.contains("Z")) this else this + "Z"}")
    context.getString(R.string.time_parsing_error)
}

fun String.fromIso8601ToInstant() =
    java.time.Instant.from(
        DateTimeFormatter.ISO_DATE_TIME.parse(this)
    ).toKotlinInstant().toMoscowDateTime()

fun nowAsIso8601() = java.time.Instant.now().toString()*/
