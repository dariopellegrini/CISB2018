package com.dariopellegrini.cisb2018.controllers

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.controllers.base.BaseController
import com.dariopellegrini.cisb2018.database.DatabaseCore
import com.dariopellegrini.cisb2018.model.Talk
import kotlinx.android.synthetic.main.controller_talk.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.view.ViewTreeObserver.OnScrollChangedListener
import kotlinx.android.synthetic.main.row_talk.view.*


class TalkController(): BaseController() {

    var talk: Talk? = null
    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        val inflate = inflater.inflate(R.layout.controller_talk, container, false)
        return inflate
    }

    @SuppressLint("SetTextI18n")
    override fun onViewBound(view: View) {
        super.onViewBound(view)

        talk?.let { talk ->
            Glide.with(context).load(talk.image).into(talkImageView)
            talk.speaker?.let { speaker ->
                Glide.with(context).load(speaker.image).into(speakerImageView)
                speakerTextView.text = "${speaker.name} ${speaker.surname}"
                speakerRoleTextView.text = speaker.description
            }

            if (talk.startDate != null && talk.endDate != null) {
                hoursTextView.text = "${ DateFormat.format("HH:mm", talk.startDate)} - ${ DateFormat.format("HH:mm", talk.endDate)}"
            }
            titleTextView.text = talk.title
            descriptionTextView.text = talk.description

            floatingActionButton.imageResource = if (DatabaseCore.isFavourited(talk._id)) R.mipmap.ic_favourite else R.mipmap.ic_favourite_border
            floatingActionButton.onClick {
                val newFavourite = DatabaseCore.toggleFavourite(talk._id)
                floatingActionButton.imageResource = if (newFavourite) R.mipmap.ic_favourite else R.mipmap.ic_favourite_border
            }
        }

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.getScrollY() // For ScrollView
            headerFrameLayout.y = -scrollY * 0.5f

        }
    }

    override fun getTitle(): String? {
        return super.getTitle()
    }
}