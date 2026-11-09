package com.jami3ty.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jami3ty.app.data.CivicReport
import com.jami3ty.app.data.FirestoreRepository
import com.jami3ty.app.data.Resource
import com.jami3ty.app.security.BiometricAuthManager
import com.jami3ty.app.security.SecurityManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var biometricAuthManager: BiometricAuthManager
    private lateinit var securityManager: SecurityManager
    private val firestoreRepository = FirestoreRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biometricAuthManager = BiometricAuthManager(this)
        securityManager = SecurityManager(this)

        executeBiometricLogin()
    }

    private fun executeBiometricLogin() {
        when (val status = biometricAuthManager.checkBiometricAvailability()) {
            is BiometricAuthManager.BiometricStatus.Ready -> {
                biometricAuthManager.authenticate(
                    title = "Connexion Jami3ty",
                    subtitle = "Validez votre empreinte ou Face ID",
                    onSuccess = {
                        securityManager.saveUserSession("STU_1001", "student@jami3ty.ac.ma")
                        securityManager.saveAuthToken("token_sample_sec_123")
                        Toast.makeText(this, "Bienvenue sur Jami3ty !", Toast.LENGTH_SHORT).show()
                    },
                    onError = { code, err ->
                        Toast.makeText(this, "Erreur ($code): $err", Toast.LENGTH_SHORT).show()
                    },
                    onFailed = {
                        Toast.makeText(this, "Échec de l'authentification", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            is BiometricAuthManager.BiometricStatus.Unavailable -> {
                Toast.makeText(this, status.reason, Toast.LENGTH_LONG).show()
            }
        }
    }
}
