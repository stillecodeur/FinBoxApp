package com.anirudh.finboxapp.utils

import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    fun convertLongToTime(time: kotlin.Long): String {
        val date = java.sql.Date(time)
        val format = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss")
        return format.format(date)
    }
}