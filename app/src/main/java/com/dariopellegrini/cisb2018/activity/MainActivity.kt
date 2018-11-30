package com.dariopellegrini.cisb2018.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.controllers.TabController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)
        router?.let { router ->
            if (!router.hasRootController()) {
                router.setRoot(RouterTransaction.with(TabController()))
            }
        }
    }

    override fun onBackPressed() {
        if (router == null || !router!!.handleBack()) {
            super.onBackPressed()
        }
    }
}
