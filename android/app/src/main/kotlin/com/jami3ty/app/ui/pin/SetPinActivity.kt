package com.jami3ty.app.ui.pin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.jami3ty.app.R
import com.jami3ty.app.ui.main.MainActivity

class SetPinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_pin)
        val et = findViewById<android.widget.EditText>(R.id.etPin)
        val btn = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnsavPin)
        btn.setOnClickListener {
            val pin = et.text.toString()
            if (pin.length == 4) {
                savePin(pin)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else et.error = getString(R.string.pin_invalid)
        }
    }
    private fun savePin(pin: String) {
        val masterKey = MasterKey.Builder(this).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val prefs = EncryptedSharedPreferences.create(this, "jami3ty_secure", masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
        prefs.edit().putString("user_pin", pin).apply()
    }
}