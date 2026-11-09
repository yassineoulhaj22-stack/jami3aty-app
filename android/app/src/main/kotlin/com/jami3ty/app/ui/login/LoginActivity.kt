package com.jami3ty.app.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.jami3ty.app.R
import com.jami3ty.app.ui.main.MainActivity
import com.jami3ty.app.ui.pin.SetPinActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<MaterialButton>(R.id.btnLogin).setOnClickListener {
            val bm = BiometricManager.from(this)
            if (bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS
            ) {
                authenticate()
            } else {
                goTo(SetPinActivity::class.java)
            }
        }
    }

    private fun authenticate() {
        val executor = ContextCompat.getMainExecutor(this)
        val prompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                goTo(MainActivity::class.java)
            }

            override fun onAuthenticationError(code: Int, message: CharSequence) {
                if (code == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    goTo(SetPinActivity::class.java)
                }
            }
        })
        prompt.authenticate(
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authenticate")
                .setSubtitle("Verify your identity")
                .setNegativeButtonText("Use PIN")
                .build()
        )
    }

    private fun goTo(target: Class<*>) {
        startActivity(Intent(this, target))
        finish()
    }
}
