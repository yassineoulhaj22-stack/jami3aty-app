package com.jami3ty.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.jami3ty.app.R
import com.jami3ty.app.data.repo.AuthRepo
import com.jami3ty.app.data.repo.FirestoreRepo
import com.jami3ty.app.data.session.SessionStore
import com.jami3ty.app.ui.common.barcodeBitmap
import com.jami3ty.app.ui.common.canUseBiometric
import com.jami3ty.app.ui.login.LoginActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = AuthRepo()
        val session = SessionStore(requireContext())
        val uid = auth.uid ?: return

        view.findViewById<ImageView>(R.id.ivBarcode)
            .setImageBitmap(barcodeBitmap(uid, 800, 200))

        val switch = view.findViewById<SwitchCompat>(R.id.switchBiometric)
        val canUseBio = requireContext().canUseBiometric()
        switch.isEnabled = canUseBio
        switch.isChecked = canUseBio && session.biometricEnabled
        switch.setOnCheckedChangeListener { _, checked -> session.biometricEnabled = checked }

        view.findViewById<MaterialButton>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            session.clear()
            startActivity(
                Intent(requireContext(), LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val student = runCatching { FirestoreRepo().getStudent(uid) }.getOrNull()
            view.findViewById<TextView>(R.id.tvName).text = student?.name ?: ""
            view.findViewById<TextView>(R.id.tvEmail).text = student?.email ?: (auth.email ?: "")
            view.findViewById<TextView>(R.id.tvUniversity).text = student?.university ?: ""
        }
    }
}
