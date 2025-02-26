package com.greenfieldxd.easybalance.data.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun todayDateTime(): String {
    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(zone)

    val date = localDateTime.date.toString().substring(8, 10) + "." +
            localDateTime.date.toString().substring(5, 7) + "." +
            localDateTime.date.toString().substring(0, 4)

    val time = localDateTime.time.toString().substring(0, 8)

    return "$date $time"
}