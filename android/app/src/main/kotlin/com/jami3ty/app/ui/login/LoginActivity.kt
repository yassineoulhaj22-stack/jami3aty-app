package com.jami3ty.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.jami3ty.app.R
import com.jami3ty.app.ui.main.MainActivity
import com.jami3ty.app.ui.pin.SetPinActivity
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private val executor = Executor { Handler(Looper.getMainExecutor()).post(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogin)
        btn.setOnClickListener {
            val bm = BiometricManager.from(this)
            if (bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
                authenticate()
            } else {
                startActivity(Intent(this, SetPinActivity::class.java))
                finish()
            }
        }
    }

    private fun authenticate() {
        val prompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(r: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(r)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            override fun onAuthenticationError(code: Int, s: CharSequence) {
                super.onAuthenticationError(code, s)
                if (code == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    startActivity(Intent(this@LoginActivity, SetPinActivity::class.java))
                    finish()
                }
            }
        })
        prompt.authenticate(BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate")
            .setSubtitle("Verify your identity")
            .setNegativeButtonText("Use PIN")
            .build())
    }
}