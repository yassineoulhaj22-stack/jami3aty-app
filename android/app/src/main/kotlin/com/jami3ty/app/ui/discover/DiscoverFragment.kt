package com.jami3ty.app.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jami3ty.app.R
import com.jami3ty.app.ui.common.placeholderView

class DiscoverFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = placeholderView(getString(R.string.nav_discover))
}
