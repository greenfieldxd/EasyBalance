package com.greenfieldxd.easybalance.data.utils

import java.text.NumberFormat
import java.util.Locale

actual fun formatToCurrency(amount: Double, locale: String): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag(locale.replace('_', '-')))
    return currencyFormatter.format(amount).replace("Br", "BYN")
}