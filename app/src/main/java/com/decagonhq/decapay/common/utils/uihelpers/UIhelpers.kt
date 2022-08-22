package com.decagonhq.decapay.common.utils.uihelpers

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.R

fun Fragment.showPleaseWaitAlertDialog(): AlertDialog {
    return showCustomDialog(
        requireContext(),
        resources,
        R.layout.custom_login_wait_dialog,
        false
    )
}

fun showCustomDialog(
    context: Context,
    resources: Resources,
    layout: Int,
    cancelable: Boolean = true
): AlertDialog {
    val view = View.inflate(context, layout, null)
    val builder = AlertDialog.Builder(context)
    builder.setView(view)
    builder.setCancelable(cancelable)
    val dialog = builder.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
    val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
    dialog!!.window?.setLayout(width, height)

    return dialog
}

fun Fragment.hideKeyboard() {
    val hideKeyboard = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var currentFocus = requireActivity().currentFocus
    if (currentFocus == null) {
        currentFocus = View(requireActivity())
    }
    hideKeyboard.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}
