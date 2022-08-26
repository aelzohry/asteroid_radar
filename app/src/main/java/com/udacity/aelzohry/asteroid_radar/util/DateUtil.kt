package com.udacity.aelzohry.asteroid_radar.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getTodayFormatted(format: String = Constants.API_QUERY_DATE_FORMAT): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    fun getSevenDaysLaterFormatted(format: String = Constants.API_QUERY_DATE_FORMAT): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

}