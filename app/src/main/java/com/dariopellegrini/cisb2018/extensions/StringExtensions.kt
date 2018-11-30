package com.dariopellegrini.cisb2018.extensions

import java.text.SimpleDateFormat
import java.util.*

val String.date: Date?
    get() {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        return try {
            format.parse(this)
        } catch (e: Exception) {
            null
        }
    }