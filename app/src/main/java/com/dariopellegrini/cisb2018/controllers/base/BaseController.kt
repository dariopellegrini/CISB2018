package com.dariopellegrini.cisb2018.controllers.base

import com.bluelinelabs.conductor.Controller
import android.app.Activity
import android.content.Context
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.dariopellegrini.cisb2018.R
import kotlinx.android.extensions.LayoutContainer

abstract class BaseController: Controller(), LayoutContainer {
    lateinit var context: Context
    private var _containerView: View? = null
    override val containerView: View?
        get() = _containerView

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View
    var toolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container).also {
            context = it.context
            _containerView = it
        }
        view.findViewById<Toolbar>(R.id.toolbar)?.let {
            toolbar = it
        }
        onViewBound(view)
        return view
    }

    protected open fun onViewBound(view: View) {
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        setTitle()

        if (router.backstackSize > 1) {
            toolbar?.setNavigationIcon(R.drawable.back_button)
            toolbar?.setNavigationOnClickListener {
                router.popCurrentController()
            }
        }
    }

    protected fun setTitle() {
        val title = getTitle() ?: ""
        toolbar?.let {
            it.title = title
        }
    }

    protected open fun getTitle(): String? {
        return null
    }

    fun getString(id: Int): String {
        return activity?.getString(id) ?: ""
    }

    fun getString(id: Int, vararg formatArgs: String): String {
        return activity?.getString(id, *formatArgs) ?: ""
    }

    // Keyboard
    fun hideKeyboard() {
        activity?.let {
            activity ->
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard() {
        activity?.let {
            activity ->
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }
}