package com.dariopellegrini.cisb2018.database.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TalkRealm(
    @PrimaryKey
    var _id: String = "",
    var title: String? = null,
    var description: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var image: String? = null,
    var speaker: SpeakerRealm? = null,
    var isFavourite: Boolean = false): RealmObject()