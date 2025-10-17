package com.example.elqh

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elqh.adapters.RecetaAdapter
import com.example.elqh.models.Receta
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class m_recetas : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mrecetas)

        // Ajusta el padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val seleccion = intent.getStringArrayListExtra("seleccion") ?: arrayListOf()
        val recycler = findViewById<RecyclerView>(R.id.rycrecetas)
        recycler.layoutManager = LinearLayoutManager(this)

        val db = Firebase.firestore

        // Estrategia Offline-First: Primero intenta cargar desde la caché
        db.collection("recetas")
            .get(Source.CACHE)
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    // Si la caché está vacía, es la primera vez que se abre la app o se limpió la caché.
                    // Se procede a buscar los datos en el servidor.
                    Log.d("m_recetas", "Caché vacía, intentando obtener del servidor...")
                    obtenerRecetasDelServidor(db, recycler, seleccion)
                } else {
                    // Si hay datos en la caché, se cargan inmediatamente.
                    Log.d("m_recetas", "Recetas cargadas desde la CACHÉ.")
                    procesarYMostrarRecetas(result, recycler, seleccion)
                }
            }
            .addOnFailureListener {
                // Este bloque se ejecutaría si hay un error al leer la caché (muy raro).
                // Como plan B, se intenta obtener del servidor.
                Log.w("m_recetas", "Fallo al leer de caché, intentando desde servidor.")
                obtenerRecetasDelServidor(db, recycler, seleccion)
            }

        // Configuración del botón para volver
        val btnAtras = findViewById<Button>(R.id.btn_atras)
        btnAtras.setOnClickListener { finish() }
    }

    private fun obtenerRecetasDelServidor(db: FirebaseFirestore, recycler: RecyclerView, seleccion: ArrayList<String>) {
        db.collection("recetas").get(Source.SERVER)
            .addOnSuccessListener { result ->
                Log.d("m_recetas", "Recetas cargadas desde el SERVIDOR.")
                procesarYMostrarRecetas(result, recycler, seleccion)
            }
            .addOnFailureListener { exception ->
                Log.e("m_recetas", "Error definitivo al obtener documentos.", exception)
                Toast.makeText(this, "No se pudieron cargar las recetas. Revisa tu conexión.", Toast.LENGTH_LONG).show()
            }
    }

    private fun procesarYMostrarRecetas(result: com.google.firebase.firestore.QuerySnapshot, recycler: RecyclerView, seleccion: ArrayList<String>) {
        val recetas = result.map { document ->
            // Convierte cada documento de Firestore en un objeto Receta
            val id = document.id
            val nombre = document.getString("nombre")!!
            val ingredientes = document.get("ingredientes") as List<String>
            val instrucciones = document.getString("instrucciones")!!
            val imagenNombre = document.getString("imagen")!!
            val imagenResId = resources.getIdentifier(imagenNombre, "drawable", packageName)

            Receta(id, nombre, ingredientes, imagenResId, instrucciones)
        }

        // Filtra las recetas y configura el adaptador del RecyclerView
        val recetasFiltradas = if (seleccion.isEmpty()) {
            recetas
        } else {
            recetas.filter { receta ->
                receta.ingredientes.any { ingrediente -> ingrediente in seleccion }
            }
        }

        // Configura el adaptador con la lista de recetas filtradas
        recycler.adapter = RecetaAdapter(recetasFiltradas) { receta ->
            val intent = Intent(this, RecetaDetalleActivity::class.java)
            intent.putExtra("receta", receta)
            startActivity(intent)
        }
    }
}


