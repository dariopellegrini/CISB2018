package com.dariopellegrini.cisb2018.controllers

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.controllers.base.BaseController
import com.dariopellegrini.cisb2018.database.DatabaseCore
import com.dariopellegrini.cisb2018.extensions.pushHorizontal
import com.dariopellegrini.cisb2018.rows.FullscreenMessageRow
import com.dariopellegrini.cisb2018.rows.TalkRow
import com.dariopellegrini.declarativerecycler.RecyclerManager
import kotlinx.android.synthetic.main.controller_favourites.*

class FavouritesController: BaseController() {

    var recyclerManager: RecyclerManager? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        val inflate = inflater.inflate(R.layout.controller_favourites, container, false)
        return inflate
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        retainViewMode = Controller.RetainViewMode.RETAIN_DETACH

        recyclerManager = RecyclerManager(recyclerView, LinearLayoutManager(context))
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        recyclerManager?.clear()
        recyclerManager?.push(DatabaseCore.favouriteTalks.filter { !it.isNotARealTalk }.map {
            TalkRow(it) {
                val controller = TalkController()
                controller.talk = it
                router?.pushHorizontal(controller)
            }
        })
        if (recyclerManager?.rowsSize == 0) {
            recyclerManager?.push(FullscreenMessageRow(getString(R.string.no_favourite)))
        }
        recyclerManager?.reload()
    }

    override fun getTitle(): String? {
        return getString(R.string.favourites)
    }
}