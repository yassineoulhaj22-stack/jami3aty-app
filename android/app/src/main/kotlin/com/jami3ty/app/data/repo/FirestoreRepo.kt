package com.jami3ty.app.data.repo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
class FirestoreRepo {
    private val db = FirebaseUtils.getInstance()
    suspend fun getUser(id: String): Map<String, Any>? = try { db.collection("users").document(id).get().await().data } catch (e: Exception) { null }
    suspend fun savePost(post: Map<String, Any>) { db.collection("posts").add(post).await() }
}