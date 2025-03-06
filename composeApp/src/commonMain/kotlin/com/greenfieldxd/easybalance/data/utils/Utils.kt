package com.greenfieldxd.easybalance.data.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.floor
import kotlin.math.pow

expect fun formatToCurrency(amount: Double, locale: String = "be_BY"): String

fun truncateToDecimals(value: Double, decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return floor(value * factor) / factor
}

fun todayDateTime(): String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()

fun formatDate(dateTime: String): String {
    val parsedDateTime = LocalDateTime.parse(dateTime)
    return buildString {
        append(parsedDateTime.dayOfMonth.toString().padStart(2, '0')).append(".")
        append(parsedDateTime.monthNumber.toString().padStart(2, '0')).append(".")
        append(parsedDateTime.year).append(" ")
        append(parsedDateTime.hour.toString().padStart(2, '0')).append(":")
        append(parsedDateTime.minute.toString().padStart(2, '0')).append(":")
        append(parsedDateTime.second.toString().padStart(2, '0'))
    }
}