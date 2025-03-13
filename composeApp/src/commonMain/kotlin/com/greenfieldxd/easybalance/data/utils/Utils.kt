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

fun formatDate(dateTime: LocalDateTime): String {
    return buildString {
        append(dateTime.dayOfMonth.toString().padStart(2, '0')).append(".")
        append(dateTime.monthNumber.toString().padStart(2, '0')).append(".")
        append(dateTime.year).append(" ")
        append(dateTime.hour.toString().padStart(2, '0')).append(":")
        append(dateTime.minute.toString().padStart(2, '0')).append(":")
        append(dateTime.second.toString().padStart(2, '0'))
    }
}