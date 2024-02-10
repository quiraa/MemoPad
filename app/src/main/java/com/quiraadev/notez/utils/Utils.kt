package com.quiraadev.notez.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun generateDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }
}