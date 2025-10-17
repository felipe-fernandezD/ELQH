package com.example.elqh

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class ElqhApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Habilita la persistencia de datos offline para Firestore.
        // Esto guardará una copia de los datos a los que accedas
        // y permitirá que tu app los lea incluso sin conexión.
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        Firebase.firestore.firestoreSettings = settings
        // -----------------------------
    }
}