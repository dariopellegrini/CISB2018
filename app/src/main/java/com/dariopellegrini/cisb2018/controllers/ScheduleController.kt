package com.dariopellegrini.cisb2018.controllers

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.controllers.base.BaseController
import com.dariopellegrini.cisb2018.extensions.pushHorizontal
import com.dariopellegrini.cisb2018.model.Talk
import com.dariopellegrini.cisb2018.rows.ErrorRow
import com.dariopellegrini.cisb2018.rows.OtherScheduleRow
import com.dariopellegrini.cisb2018.rows.ProgressRow
import com.dariopellegrini.cisb2018.rows.TalkRow
import com.dariopellegrini.cisb2018.viewmodels.ScheduleViewModel
import com.dariopellegrini.cisb2018.viewmodels.ScheduleViewModel.Status.*
import com.dariopellegrini.declarativerecycler.RecyclerManager
import com.dariopellegrini.declarativerecycler.Row
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.controller_schedule.*
import org.jetbrains.anko.longToast

class ScheduleController: BaseController() {

    var recyclerManager: RecyclerManager? = null
    val viewModel = ScheduleViewModel()
    val compositeDisposable = CompositeDisposable()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_schedule, container, false)
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        retainViewMode = Controller.RetainViewMode.RETAIN_DETACH

        recyclerManager = RecyclerManager(recyclerView, LinearLayoutManager(context))
        viewModel.status.observable.subscribe {
            when(it) {
                is Start -> recyclerManager?.clear(true)
                is Loading -> recyclerManager?.pushClearing(ProgressRow(getString(R.string.loading)))
                is LoadingSilent -> swipeRefreshLayout.isRefreshing = true
                is Talks -> {
                    populate(it.talks)
                }
                is Success -> swipeRefreshLayout.isRefreshing = false
                is Error -> recyclerManager?.pushClearing(ErrorRow(getString(it.errorStringRes)) {
                    viewModel.getTalks()
                })
                is ErrorSilent -> context.longToast(it.errorStringRes).show()
            }
        }.addTo(compositeDisposable)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getTalks()
        }
        viewModel.getTalks()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun getTitle(): String? {
        return getString(R.string.schedule)
    }

    private fun populate(talks: List<Talk>) {
        recyclerManager?.clear()
        recyclerManager?.append(talks.map {
            if (!it.isNotARealTalk) {
                TalkRow(it) {
                    val controller = TalkController()
                    controller.talk = it
                    router?.pushHorizontal(controller)
                }
            } else {
                OtherScheduleRow(it)
            }
        })
        recyclerManager?.reload()
    }

    private fun RecyclerManager.pushClearing(row: Row) {
        this.clear()
        this.push(row)
        this.reload()
    }
}