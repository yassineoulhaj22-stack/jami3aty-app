package com.jami3ty.app.ui.inbox

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jami3ty.app.R
import com.jami3ty.app.data.repo.AuthRepo
import com.jami3ty.app.data.repo.FirestoreRepo
import com.jami3ty.app.ui.common.ListScreen
import com.jami3ty.app.ui.common.Row
import com.jami3ty.app.ui.common.formatDate
import kotlinx.coroutines.launch

/** Shows only the reports published by the signed-in student. */
class InboxFragment : Fragment(R.layout.fragment_list) {

    private var screen: ListScreen? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen = ListScreen(view, R.string.inbox_empty)
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            val uid = AuthRepo().uid
            val mine = runCatching { FirestoreRepo().getReports() }
                .getOrDefault(emptyList())
                .filter { it.authorId == uid }
            screen?.show(mine.map { Row(it.title, formatDate(requireContext(), it.createdAt)) })
        }
    }
}
