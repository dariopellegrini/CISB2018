package com.dariopellegrini.cisb2018.api

import com.dariopellegrini.cisb2018.extensions.fromJson
import com.dariopellegrini.cisb2018.model.Talk
import com.dariopellegrini.spike.TargetType
import com.dariopellegrini.spike.network.SpikeMethod
import com.google.gson.Gson

sealed class APITarget : TargetType {
    object Talks: APITarget()

    override val baseURL: String
        get() = "https://aqueous-caverns-34933.herokuapp.com/"

    override val path: String
        get() {
            return when(this) {
                is Talks -> "talks?\$sort={\"startTime\":1}"
            }
        }

    override val headers: Map<String, String>?
        get() = mapOf("Content-Type" to "application/json;")

    override val method: SpikeMethod
        get() {
            return when(this) {
                is Talks -> SpikeMethod.GET
            }
        }

    override val parameters: Map<String, Any>?
        get() = null

    override val successClosure: ((String, Map<String, String>?) -> Any?)?
        get() = { result, headers ->

            when(this) {
                is Talks -> {
                    Gson().fromJson<List<Talk>>(result)
                }
            }
        }

    override val errorClosure: ((String, Map<String, String>?) -> Any?)?
        get() = null
}