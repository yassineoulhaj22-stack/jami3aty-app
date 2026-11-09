package com.jami3ty.app.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.jami3ty.app.R
import com.jami3ty.app.data.model.Student
import com.jami3ty.app.data.repo.AuthRepo
import com.jami3ty.app.data.repo.FirestoreRepo
import com.jami3ty.app.data.session.SessionStore
import com.jami3ty.app.ui.main.MainActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private val auth = AuthRepo()
    private val repo = FirestoreRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etRegName)
        val etUniversity = findViewById<EditText>(R.id.etRegUniversity)
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)

        findViewById<MaterialButton>(R.id.btnRegister).setOnClickListener {
            val name = etName.text.toString().trim()
            val university = etUniversity.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                name.isEmpty() || university.isEmpty() || email.isEmpty() || password.isEmpty() ->
                    toast(R.string.register_fill_fields)
                password.length < 6 -> toast(R.string.register_password_short)
                else -> lifecycleScope.launch {
                    try {
                        val uid = auth.register(email, password)
                        repo.saveStudent(Student(uid, name, email, university, System.currentTimeMillis()))
                        SessionStore(this@RegisterActivity).uid = uid
                        startActivity(
                            Intent(this@RegisterActivity, MainActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        )
                    } catch (e: Exception) {
                        toast(R.string.register_failed)
                    }
                }
            }
        }
    }

    private fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}
