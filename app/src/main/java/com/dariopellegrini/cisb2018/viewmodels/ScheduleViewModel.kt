package com.dariopellegrini.cisb2018.viewmodels

import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.api.APITarget
import com.dariopellegrini.cisb2018.database.DatabaseCore
import com.dariopellegrini.cisb2018.model.Talk
import com.dariopellegrini.cisb2018.rx.Variable
import com.dariopellegrini.spike.SpikeProvider

/**
 * Created by Dario on 26/11/2018.
 * Dario Pellegrini Brescia
 */

class ScheduleViewModel {
    var talks = DatabaseCore.talks

    sealed class Status {
        object Start: Status()
        object Loading: Status()
        object LoadingSilent: Status()
        class Talks(val talks: List<Talk>): Status()
        object Success: Status()
        class Error(val errorStringRes: Int): Status()
        class ErrorSilent(val errorStringRes: Int): Status()
    }

    val status = Variable<Status>(Status.Start)

    fun getTalks() {
        status.value = if (talks.isNotEmpty()) Status.LoadingSilent else Status.Loading
        this.talks = DatabaseCore.talks
        if (talks.isNotEmpty()) {
            status.value = Status.Talks(this.talks)
        }
        val provider = SpikeProvider<APITarget>()
        provider.requestTypesafe<List<Talk>, Any>(APITarget.Talks, {
                results ->
            results.computedResult?.let { talks ->
                DatabaseCore.addTalks(talks)
                this.talks = talks
                status.value = Status.Talks(this.talks)
                status.value = Status.Success
            } ?: run {
                status.value = if (talks.isNotEmpty())
                    Status.ErrorSilent(R.string.something_went_wrong)
                else Status.Error(R.string.something_went_wrong)
            }
        }, {
            status.value = if (talks.isNotEmpty())
                Status.ErrorSilent(R.string.something_went_wrong)
            else Status.Error(R.string.something_went_wrong)
        })
    }
}