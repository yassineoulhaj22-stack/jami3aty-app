package com.jami3ty.app.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

data class CivicReport(
    val id: String = "",
    val studentId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val location: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val status: String = "Pending"
)

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class FirestoreRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val reportsCollection = db.collection("reports")

    suspend fun submitReport(report: CivicReport): Resource<String> {
        return try {
            val docRef = reportsCollection.document()
            val finalReport = report.copy(id = docRef.id)
            docRef.set(finalReport).await()
            Resource.Success(docRef.id)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun fetchReports(): Resource<List<CivicReport>> {
        return try {
            val snapshot = reportsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            val reports = snapshot.toObjects(CivicReport::class.java)
            Resource.Success(reports)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
