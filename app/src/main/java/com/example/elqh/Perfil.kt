package com.example.elqh

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elqh.adapters.RecetaAdapter
import com.example.elqh.models.Receta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class Perfil : AppCompatActivity() {

    // --- Propiedades con inicialización "lazy" ---
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val db: FirebaseFirestore by lazy { Firebase.firestore }
    private val storage by lazy { Firebase.storage.reference } // Propiedad para Firebase Storage

    private val recyclerFavoritas: RecyclerView by lazy { findViewById(R.id.rycfredeta) }
    private val ivAvatar: CircleImageView by lazy { findViewById(R.id.ivAvatar) }
    private val btnChangePhoto: ImageButton by lazy { findViewById(R.id.btnChangePhoto) } // Botón de la cámara

    private val recetasAdapter: RecetaAdapter by lazy {
        RecetaAdapter(listaRecetasFavoritas) { receta ->
            val intent = Intent(this, RecetaDetalleActivity::class.java)
            intent.putExtra("receta", receta)
            startActivity(intent)
        }
    }

    private val listaRecetasFavoritas = mutableListOf<Receta>()
    private var tempImageUri: Uri? = null // URI temporal para la foto

    // --- INICIO: COMPONENTES DE LA CÁMARA ---

    // 1. Lanzador para la toma de la foto
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                tempImageUri?.let { uri ->
                    ivAvatar.setImageURI(uri) // Muestra la nueva foto inmediatamente
                    uploadImageToFirebase(uri) // Sube la foto a Firebase
                }
            } else {
                Toast.makeText(this, "No se tomó ninguna foto.", Toast.LENGTH_SHORT).show()
            }
        }

    // 2. Lanzador para solicitar el permiso de la cámara
    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launchCamera() // Si el permiso es concedido, lanza la cámara
            } else {
                Toast.makeText(this, "Permiso de cámara denegado.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val tvAlias = findViewById<TextView>(R.id.tvAlias)
        val btnAtras = findViewById<Button>(R.id.btn_atrasme)

        setupRecyclerView()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            tvAlias.text = currentUser.email ?: "Usuario"
            cargarRecetasFavoritas(currentUser.uid)
            loadProfileImage(currentUser.uid)
        } else {
            // Manejo de usuario no logueado
        }

        btnAtras.setOnClickListener { finish() }

        // --- Asignación del listener al botón de la cámara ---
        btnChangePhoto.setOnClickListener {
            checkCameraPermissionAndLaunch()
        }
    }

    // 3. Función para verificar el permiso y lanzar la cámara
    private fun checkCameraPermissionAndLaunch() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchCamera() // Permiso concedido
            }

            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA) // Pedir permiso
            }
        }
    }

    // 4. Función para preparar el intent de la cámara y lanzarlo
// 4. Función para preparar el intent de la cámara y lanzarlo
    private fun launchCamera() {
        val imageFile = File(filesDir, "temp_image_${System.currentTimeMillis()}.jpg")
        tempImageUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            imageFile
        )

        // Este bloque ya está corregido y es correcto
        tempImageUri?.let { uri ->
            takePictureLauncher.launch(uri)
        }
    }


    // 5. Función para subir la imagen a Firebase Storage y actualizar Firestore
    private fun uploadImageToFirebase(uri: Uri) {
        val user = auth.currentUser ?: return
        val profileImageRef = storage.child("profile_images/${user.uid}.jpg")

        profileImageRef.putFile(uri)
            .addOnSuccessListener {
                profileImageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    db.collection("usuarios").document(user.uid)
                        .update("profileImageUrl", downloadUrl.toString())
                        .addOnSuccessListener {
                            Toast.makeText(this, "Foto de perfil actualizada", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
            }
    }

    // --- FIN: COMPONENTES DE LA CÁMARA ---

    private fun loadProfileImage(userId: String) {
        db.collection("usuarios").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.contains("profileImageUrl")) {
                    val imageUrl = document.getString("profileImageUrl")
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.chefsito)
                        .error(R.drawable.chefsito)
                        .into(ivAvatar)
                }
            }
    }

    private fun setupRecyclerView() {
        recyclerFavoritas.layoutManager = LinearLayoutManager(this)
        recyclerFavoritas.adapter = recetasAdapter
    }

    private fun cargarRecetasFavoritas(userId: String) {
        // ... (tu código para cargar recetas se mantiene igual)
        db.collection("usuarios").document(userId).collection("favoritas")
            .get()
            .addOnSuccessListener { favoriteSnapshots ->
                if (favoriteSnapshots.isEmpty) {
                    listaRecetasFavoritas.clear()
                    recetasAdapter.notifyDataSetChanged()
                    return@addOnSuccessListener
                }

                val idsFavoritas = favoriteSnapshots.map { it.id }

                db.collection("recetas").whereIn("__name__", idsFavoritas)
                    .get(Source.DEFAULT)
                    .addOnSuccessListener { recipeSnapshots ->
                        val recetas = recipeSnapshots.mapNotNull { document ->
                            try {
                                val id = document.id
                                val nombre = document.getString("nombre")!!
                                val ingredientes = document.get("ingredientes") as List<String>
                                val instrucciones = document.getString("instrucciones")!!
                                val imagenNombre = document.getString("imagen")!!
                                val imagenResId =
                                    resources.getIdentifier(imagenNombre, "drawable", packageName)

                                Receta(id, nombre, ingredientes, imagenResId, instrucciones)
                            } catch (e: Exception) {
                                Log.e("perfil.kt", "Error parseando la receta ${document.id}", e)
                                null
                            }
                        }

                        listaRecetasFavoritas.clear()
                        listaRecetasFavoritas.addAll(recetas)
                        recetasAdapter.notifyDataSetChanged()
                    }
                    .addOnFailureListener { e ->
                        Log.e("perfil.kt", "Error al cargar detalles de recetas favoritas", e)
                        Toast.makeText(
                            this,
                            "No se pudieron cargar las recetas favoritas.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .addOnFailureListener { e ->
                Log.e("perfil.kt", "Error al cargar IDs de favoritas", e)
            }
    }
}
