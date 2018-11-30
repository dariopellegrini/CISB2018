package com.dariopellegrini.cisb2018.extensions

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler

/**
 * Created by Dario on 26/11/2018.
 * Dario Pellegrini Brescia
 */

fun Router.pushHorizontal(controller: Controller) {
    this.pushController(RouterTransaction.with(controller)
        .pushChangeHandler(HorizontalChangeHandler())
        .popChangeHandler(HorizontalChangeHandler()))
}