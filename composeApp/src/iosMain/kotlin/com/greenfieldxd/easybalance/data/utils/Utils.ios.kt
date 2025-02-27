package com.greenfieldxd.easybalance.data.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumber
import platform.Foundation.numberWithDouble

actual fun formatToCurrency(amount: Double, locale: String): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        this.locale = NSLocale(locale)
    }
    val nsNumber = NSNumber.numberWithDouble(amount)
    return formatter.stringFromNumber(nsNumber)?.replace("Br", "BYN") ?: "$amount BYN"
}
