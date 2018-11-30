package com.dariopellegrini.cisb2018.database.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SpeakerRealm(
    var name: String? = null,
    var surname: String? = null,
    var description: String? = null,
    var image: String? = null): RealmObject()