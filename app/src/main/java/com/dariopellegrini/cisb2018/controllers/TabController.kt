package com.dariopellegrini.cisb2018.controllers

import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.controllers.base.BaseController
import kotlinx.android.synthetic.main.controller_tab.*

class TabController: BaseController() {

    lateinit var childRouter: Router

    sealed class MenuElement {
        object Schedule: MenuElement()
        object Favourites: MenuElement()
        object Venue: MenuElement()

        val controller: BaseController
            get() = when(this) {
                is Schedule -> ScheduleController()
                is Favourites -> FavouritesController()
                is Venue -> VenueController()
            }
    }

    var currentMenuElement: MenuElement? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        val inflate = inflater.inflate(R.layout.controller_tab, container, false)
        return inflate
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        childRouter = getChildRouter(tabContainer)
        retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            onNavigationItemSelected(menuItem)
        }
        bottomNavigationView.selectedItemId = R.id.action_schedule
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        val element = when (item.itemId) {
            R.id.action_schedule -> {
                MenuElement.Schedule
            }
            R.id.action_favourites -> {
                MenuElement.Favourites
            }
            R.id.action_venue -> {
                MenuElement.Venue
            }
            else -> null
        }

        if (currentMenuElement == element) {
            if (childRouter.backstackSize > 1) {
                childRouter.popToRoot()
            }
            return true
        } else {
            currentMenuElement = element
        }

        currentMenuElement?.let {

            val controller = it.controller
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            val transaction = RouterTransaction.with(controller)
            childRouter.setRoot(transaction)
        }
        return true
    }
}