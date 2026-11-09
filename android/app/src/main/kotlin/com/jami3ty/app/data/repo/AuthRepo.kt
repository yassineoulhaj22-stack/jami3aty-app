package com.jami3ty.app.data.repo

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepo {
    private val auth = FirebaseAuth.getInstance()

    val uid: String? get() = auth.currentUser?.uid
    val email: String? get() = auth.currentUser?.email

    suspend fun signIn(email: String, password: String): String =
        auth.signInWithEmailAndPassword(email, password).await().user?.uid
            ?: error("No user returned")

    suspend fun register(email: String, password: String): String =
        auth.createUserWithEmailAndPassword(email, password).await().user?.uid
            ?: error("No user returned")

    fun signOut() = auth.signOut()
}
