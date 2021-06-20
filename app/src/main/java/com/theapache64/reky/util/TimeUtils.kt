package com.theapache64.reky.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun getPercentageFinished(start: Long, now: Long, end: Long): Int {
        val totalDuration = end - start
        val timeSpent = now - start
        val percFinished = timeSpent.toFloat() / totalDuration * 100
        return percFinished.toInt()
    }

    private const val SECOND_MILLIS = 1000
    const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private val dateFormat = SimpleDateFormat(
        "EEE, MMM d, h:mm a"
    )

    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> {
                "just now"
            }
            diff < 2 * MINUTE_MILLIS -> {
                "a minute ago"
            }
            diff < 50 * MINUTE_MILLIS -> {
                "${(diff / MINUTE_MILLIS)} minutes ago"
            }
            diff < 90 * MINUTE_MILLIS -> {
                "an hour ago"
            }
            diff < 24 * HOUR_MILLIS -> {
                "${(diff / HOUR_MILLIS)} hours ago"
            }
            diff < 48 * HOUR_MILLIS -> {
                "yesterday"
            }
            else -> {
                dateFormat.format(Date(time))
            }
        }
    }
}