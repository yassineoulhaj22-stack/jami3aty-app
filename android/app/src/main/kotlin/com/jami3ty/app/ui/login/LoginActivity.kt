package com.jami3ty.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.jami3ty.app.R
import com.jami3ty.app.data.repo.AuthRepo
import com.jami3ty.app.data.session.SessionStore
import com.jami3ty.app.ui.common.canUseBiometric
import com.jami3ty.app.ui.common.promptBiometric
import com.jami3ty.app.ui.main.MainActivity
import com.jami3ty.app.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val auth = AuthRepo()
    private lateinit var session: SessionStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        session = SessionStore(this)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnBiometric = findViewById<MaterialButton>(R.id.btnBiometric)

        findViewById<MaterialButton>(R.id.btnLogin).setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                toast(R.string.login_fill_fields)
                return@setOnClickListener
            }
            lifecycleScope.launch {
                try {
                    session.uid = auth.signIn(email, password)
                    goToMain()
                } catch (e: Exception) {
                    toast(R.string.login_failed)
                }
            }
        }

        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnBiometric.setOnClickListener { unlockWithBiometric() }

        val canUseBio = canUseBiometric() && session.biometricEnabled && auth.uid != null
        btnBiometric.visibility = if (canUseBio) View.VISIBLE else View.GONE

        // Already signed in: ask for biometrics if enabled, otherwise go straight in.
        if (auth.uid != null) {
            if (canUseBio) unlockWithBiometric() else goToMain()
        }
    }

    private fun unlockWithBiometric() {
        promptBiometric(getString(R.string.biometric_title)) { goToMain() }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}
