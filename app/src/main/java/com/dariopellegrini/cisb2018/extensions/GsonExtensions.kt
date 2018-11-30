package com.dariopellegrini.cisb2018.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(jsonString: String): T? {
    return try {
        this.fromJson<T>(jsonString, object: TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }
}