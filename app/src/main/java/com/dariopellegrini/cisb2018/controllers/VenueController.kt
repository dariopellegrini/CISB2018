package com.dariopellegrini.cisb2018.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.controllers.base.BaseController
import kotlinx.android.synthetic.main.controller_venue.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.content.Intent
import android.net.Uri


class VenueController: BaseController() {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_venue, container, false)
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        retainViewMode = Controller.RetainViewMode.RETAIN_DETACH

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            headerImageView.y = -scrollView.scrollY * 0.5f
        }


        placeLinearLayout.onClick {
            val intent = Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.co.in/maps?q=${getString(R.string.location)}"))
            startActivity(intent)
        }
    }

    override fun getTitle(): String? {
        return getString(R.string.venue)
    }
}