package com.dariopellegrini.cisb2018.database

import com.dariopellegrini.cisb2018.database.model.SpeakerRealm
import com.dariopellegrini.cisb2018.database.model.TalkRealm
import com.dariopellegrini.cisb2018.model.Speaker
import com.dariopellegrini.cisb2018.model.Talk
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Dario on 26/11/2018.
 * Dario Pellegrini Brescia
 */

object DatabaseCore {
    val realm: Realm
        get() = Realm.getDefaultInstance()

    fun addTalks(talks: List<Talk>) {
        CoroutineScope(Dispatchers.IO).launch {
            realm.use { realm ->
                talks.forEach { talk ->
                    realm.executeTransaction {
                        realm.copyToRealmOrUpdate(talk.realm)
                    }
                }
            }
        }
    }

    fun isFavourited(talkId: String): Boolean {
        return realm.where(TalkRealm::class.java).equalTo("_id", talkId).findFirst()?.isFavourite ?: false
    }

    fun toggleFavourite(talkId: String): Boolean {
        realm.where(TalkRealm::class.java).equalTo("_id", talkId).findFirst()?.let {
            talk ->
            realm.executeTransaction {
                talk.isFavourite = !talk.isFavourite
            }
            return talk.isFavourite
        }
    }

    val talks: List<Talk>
        get() = realm.where(TalkRealm::class.java).findAll()
            .map { it.model }
            .sortedBy { it.startTime }

    val favouriteTalks: List<Talk>
        get() = realm.where(TalkRealm::class.java).equalTo("isFavourite", true).findAll()
            .map { it.model }
            .sortedBy { it.startTime }

    // Extensions
    private val Talk.realm: TalkRealm
        get() = TalkRealm(_id, title, description, startTime, endTime, image, speaker?.realm, isFavourited(_id))

    private val TalkRealm.model: Talk
        get() = Talk(_id, title, description, startTime, endTime, image, speaker?.model)

    private val Speaker.realm: SpeakerRealm
        get() = SpeakerRealm(name, surname, description, image)

    private val SpeakerRealm.model: Speaker
        get() = Speaker(name, surname, description, image)
}