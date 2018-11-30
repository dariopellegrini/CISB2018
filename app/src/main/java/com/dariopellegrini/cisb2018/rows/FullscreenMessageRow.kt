package com.dariopellegrini.cisb2018.rows

import android.view.View
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.declarativerecycler.Row
import kotlinx.android.synthetic.main.row_fullscreen_message.view.*

class FullscreenMessageRow(val text: String): Row {
    override val layoutID = R.layout.row_fullscreen_message

    override val configuration: ((View, Int) -> Unit)?
        get() = {
            itemView, _ ->
            itemView.messageTextView.text = text
        }
}