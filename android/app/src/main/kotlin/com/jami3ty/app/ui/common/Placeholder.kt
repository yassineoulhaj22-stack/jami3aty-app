package com.jami3ty.app.ui.common

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

/** Simple centred title view used by tabs that do not have content yet. */
fun Fragment.placeholderView(title: String): View = TextView(requireContext()).apply {
    text = title
    textSize = 24f
    setTextColor(Color.WHITE)
    setBackgroundColor(Color.parseColor("#0F0B1F"))
    gravity = Gravity.CENTER
}
