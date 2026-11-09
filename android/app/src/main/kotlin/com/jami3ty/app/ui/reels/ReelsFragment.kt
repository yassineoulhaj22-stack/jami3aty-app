package com.jami3ty.app.ui.reels
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
class ReelsFragment : Fragment() {
    override fun onCreateView(layoutRes: Int, container: ViewGroup?, savedInstanceState: Bundle?): View = View(requireContext())
    fun share(url: String) {
        val i = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, url) }
        startActivity(Intent.createChooser(i, "Share"))
    }
}