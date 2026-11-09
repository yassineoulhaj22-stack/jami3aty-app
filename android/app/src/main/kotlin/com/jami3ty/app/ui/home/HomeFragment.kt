package com.jami3ty.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jami3ty.app.R
import com.jami3ty.app.data.repo.FirestoreRepo
import com.jami3ty.app.ui.common.ListScreen
import com.jami3ty.app.ui.common.Row
import com.jami3ty.app.ui.common.formatDate
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_list) {

    private var screen: ListScreen? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen = ListScreen(view, R.string.home_empty)
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            val reports = runCatching { FirestoreRepo().getReports() }.getOrDefault(emptyList())
            screen?.show(reports.map {
                Row(it.title, "${it.authorName} · ${formatDate(requireContext(), it.createdAt)}")
            })
        }
    }
}
