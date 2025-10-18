package com.example.elqh


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elqh.models.Receta
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecetaDetalleActivity : AppCompatActivity() {

    // Declarar instancias de Firebase
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    // Variable para saber el estado actual del favorito
    private var esFavorita = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() ya no es necesario si manejas los insets manualmente
        setContentView(R.layout.activity_receta_detalle)

        // --- MANEJO DE VISTAS Y PADDING ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- OBTENCIÓN DE LA RECETA ---
        val receta = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("receta", Receta::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("receta")
        }

        // Si la receta es nula, no podemos continuar.
        if (receta == null) {
            Toast.makeText(this, "Error al cargar la receta.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // --- REFERENCIAS A LAS VISTAS DEL LAYOUT ---
        val img = findViewById<ImageView>(R.id.imgDetalle)
        val nombre = findViewById<TextView>(R.id.tvNombreDetalle)
        val ingredientesLayout = findViewById<LinearLayout>(R.id.layoutIngredientes)
        val descripcion = findViewById<TextView>(R.id.tvDescripcionDetalle)
        val btnAtras = findViewById<Button>(R.id.btn_atras_detalle)
        // Asumimos que tienes un ImageButton en tu XML con este ID
        val btnFavorito = findViewById<ImageButton>(R.id.btn_favorito)

        // --- POPULAR LA UI CON LOS DATOS DE LA RECETA ---
        img.setImageResource(receta.imagen)
        nombre.text = receta.nombre
        descripcion.text =
            receta.instrucciones.replace("\\n", "\n") // Reemplaza para mostrar saltos de línea

        // Crear checkboxes para los ingredientes
        receta.ingredientes.forEach { ingrediente ->
            val check = CheckBox(this)
            check.text = ingrediente
            ingredientesLayout.addView(check)
        }

        // --- LÓGICA DE FAVORITOS ---
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // 1. Comprueba si la receta ya está marcada como favorita al abrir la pantalla
            verificarSiEsFavorita(userId, receta.id, btnFavorito)

            // 2. Define la acción del botón de favorito
            btnFavorito.setOnClickListener {
                if (esFavorita) {
                    quitarDeFavoritos(userId, receta.id, btnFavorito)
                } else {
                    agregarAFavoritos(userId, receta.id, btnFavorito)
                }
            }
        } else {
            // Si el usuario no está logueado, puedes ocultar el botón o mostrar un mensaje
            btnFavorito.visibility = android.view.View.GONE
        }


        // --- BOTÓN DE ATRÁS ---
        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun verificarSiEsFavorita(userId: String, recetaId: String, boton: ImageButton) {
        db.collection("usuarios").document(userId)
            .collection("favoritas").document(recetaId)
            .get()
            .addOnSuccessListener { document ->
                // Si el documento existe, significa que es favorita
                esFavorita = document.exists()
                actualizarIconoFavorito(boton)
            }
            .addOnFailureListener {
                // Manejar posible error de red
                esFavorita = false
                actualizarIconoFavorito(boton)
            }
    }

    private fun agregarAFavoritos(userId: String, recetaId: String, boton: ImageButton) {
        val data = hashMapOf("agregadoEl" to System.currentTimeMillis())
        db.collection("usuarios").document(userId)
            .collection("favoritas").document(recetaId)
            .set(data)
            .addOnSuccessListener {
                esFavorita = true
                actualizarIconoFavorito(boton)
                Toast.makeText(this, "Añadida a favoritos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("Favoritos", "Error al añadir a favoritos", e)
                Toast.makeText(this, "No se pudo añadir a favoritos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun quitarDeFavoritos(userId: String, recetaId: String, boton: ImageButton) {
        db.collection("usuarios").document(userId)
            .collection("favoritas").document(recetaId)
            .delete()
            .addOnSuccessListener {
                esFavorita = false
                actualizarIconoFavorito(boton)
                Toast.makeText(this, "Quitada de favoritos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("Favoritos", "Error al quitar de favoritos", e)
                Toast.makeText(this, "No se pudo quitar de favoritos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarIconoFavorito(boton: ImageButton) {
        if (esFavorita) {
            // Asume que tienes un icono para 'corazón lleno' en tus drawables
            boton.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            // Asume que tienes un icono para 'corazón con borde' en tus drawables
            boton.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
