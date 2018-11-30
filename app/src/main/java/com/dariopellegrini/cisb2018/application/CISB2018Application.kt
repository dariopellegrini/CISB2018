package com.dariopellegrini.cisb2018.application

import android.app.Application
import com.dariopellegrini.spike.response.Spike
import io.realm.Realm
import io.realm.RealmConfiguration

class CISB2018Application: Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("cisb2018.realm").schemaVersion(2)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(configuration)
        Realm.getInstance(configuration)

        Spike.instance.configure(this)
    }
}