package com.jami3ty.app.ui.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jami3ty.app.R
import com.jami3ty.app.ui.common.ListScreen
import com.jami3ty.app.ui.common.Row

class DiscoverFragment : Fragment(R.layout.fragment_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListScreen(view, R.string.discover_empty).show(
            listOf(
                Row(getString(R.string.service_library), getString(R.string.service_library_sub)),
                Row(getString(R.string.service_career), getString(R.string.service_career_sub)),
                Row(getString(R.string.service_health), getString(R.string.service_health_sub))
            )
        )
    }
}
