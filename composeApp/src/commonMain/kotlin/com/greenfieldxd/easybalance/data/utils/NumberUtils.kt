package com.greenfieldxd.easybalance.data.utils

import kotlin.math.floor
import kotlin.math.pow

fun truncateToDecimals(value: Double, decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return floor(value * factor) / factor
}

fun formatNumber(number: Double): String {
    val str = number.toString()
    val parts = str.split(".")

    return if (parts.size == 1 || parts[1].toIntOrNull() == 0) {
        parts[0]
    } else {
        val decimalPart = parts[1].take(2).padEnd(2, '0')
        parts[0] + "." + decimalPart
    }
}

