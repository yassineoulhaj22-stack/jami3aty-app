package com.jami3ty.app.ui.report

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.jami3ty.app.R
import com.jami3ty.app.data.model.Report
import com.jami3ty.app.data.repo.AuthRepo
import com.jami3ty.app.data.repo.FirestoreRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateReportActivity : AppCompatActivity() {

    private val auth = AuthRepo()
    private val repo = FirestoreRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report)

        val etTitle = findViewById<EditText>(R.id.etReportTitle)
        val etBody = findViewById<EditText>(R.id.etReportBody)
        val btn = findViewById<MaterialButton>(R.id.btnSubmitReport)
        val success = findViewById<LottieAnimationView>(R.id.lottieSuccess)

        btn.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val body = etBody.text.toString().trim()
            val uid = auth.uid
            if (title.isEmpty() || body.isEmpty() || uid == null) {
                Toast.makeText(this, R.string.report_fill_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            btn.isEnabled = false
            lifecycleScope.launch {
                try {
                    repo.addReport(
                        Report(
                            authorId = uid,
                            authorName = auth.email.orEmpty(),
                            title = title,
                            body = body,
                            createdAt = System.currentTimeMillis()
                        )
                    )
                    success.visibility = View.VISIBLE
                    success.playAnimation()
                    Toast.makeText(this@CreateReportActivity, R.string.report_posted, Toast.LENGTH_SHORT).show()
                    delay(1200)
                    finish()
                } catch (e: Exception) {
                    btn.isEnabled = true
                    Toast.makeText(this@CreateReportActivity, R.string.report_failed, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
