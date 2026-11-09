package com.jami3ty.app
import android.app.Application
import com.google.firebase.FirebaseApp
class Jami3tyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}