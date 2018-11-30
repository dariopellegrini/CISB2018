package com.dariopellegrini.cisb2018.rows

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.View
import com.bumptech.glide.Glide
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.cisb2018.extensions.date
import com.dariopellegrini.cisb2018.model.Talk
import com.dariopellegrini.declarativerecycler.Row
import kotlinx.android.synthetic.main.row_other_schedule.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class OtherScheduleRow(val talk: Talk): Row {
    override val layoutID = R.layout.row_other_schedule

    override val configuration: ((View, Int) -> Unit)?
        @SuppressLint("SetTextI18n")
        get() = {
            itemView, _ ->
            talk.startDate?.let {
                itemView.timeTextView.text = "${ DateFormat.format("HH:mm", it)}"
            }
            itemView.titleTextView.text = talk.title
        }
}