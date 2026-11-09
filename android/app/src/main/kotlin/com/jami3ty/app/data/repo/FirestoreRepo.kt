package com.jami3ty.app.data.repo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jami3ty.app.data.model.Report
import com.jami3ty.app.data.model.Student
import kotlinx.coroutines.tasks.await

class FirestoreRepo {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getStudent(uid: String): Student? =
        db.collection("users").document(uid).get().await().toObject(Student::class.java)

    suspend fun saveStudent(student: Student) {
        db.collection("users").document(student.id).set(student).await()
    }

    suspend fun addReport(report: Report) {
        val doc = db.collection("reports").document()
        doc.set(report.copy(id = doc.id)).await()
    }

    /** Latest reports, newest first. */
    suspend fun getReports(limit: Long = 100): List<Report> {
        val snapshot = db.collection("reports")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()
        return snapshot.toObjects(Report::class.java)
    }
}
