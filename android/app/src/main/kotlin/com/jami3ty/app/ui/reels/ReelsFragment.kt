package com.jami3ty.app.ui.reels

import android.content.Intent
import androidx.fragment.app.Fragment

/** Share helper kept for future reels content. */
class ReelsFragment : Fragment() {
    fun share(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        startActivity(Intent.createChooser(intent, "Share"))
    }
}
