package com.dariopellegrini.cisb2018.rows

import android.view.View
import com.dariopellegrini.cisb2018.R
import com.dariopellegrini.declarativerecycler.Row
import kotlinx.android.synthetic.main.row_error.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ErrorRow(val text: String, val retry: () -> Unit): Row {
    override val layoutID = R.layout.row_error

    override val configuration: ((View, Int) -> Unit)?
        get() = {
            itemView, _ ->
            itemView.errorTextView.text = text
            itemView.retryButton.onClick {
                retry()
            }
        }
}