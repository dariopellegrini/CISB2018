package com.dariopellegrini.cisb2018.model

import com.dariopellegrini.cisb2018.extensions.date
import java.util.*

data class Talk(val _id: String = "",
                val title: String? = null,
                val description: String? = null,
                val startTime: String? = null,
                val endTime: String? = null,
                val image: String? = null,
                val speaker: Speaker? = null) {

    val startDate: Date?
        get() = startTime?.date

    val endDate: Date?
        get() = endTime?.date

    val durationInMinutes: Long?
        get() {
            return if (startDate != null && endDate != null) {
                val diff = endDate!!.getTime() - startDate!!.getTime()
                val seconds = diff / 1000
                val minutes = seconds / 60
                minutes
            } else {
                return null
            }
        }
    val isNotARealTalk: Boolean
        get() = description == null && image == null && speaker == null
}