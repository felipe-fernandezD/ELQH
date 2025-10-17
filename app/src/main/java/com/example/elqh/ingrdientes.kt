package com.example.elqh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elqh.adapters.MenuAdapter
import com.example.elqh.models.Categoria

class ingrdientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingrdientes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView1 = findViewById<RecyclerView>(R.id.l_ingredientes)
        val btn_atras = findViewById<Button>(R.id.btn_atrasm)
        val btn_verificar = findViewById<Button>(R.id.btn_verificar)

        val categorias = listOf(
            Categoria("Proteínas", listOf("Pollo", "Cerdo", "Vacuno", "Pescado", "Legumbres", "Huevo")),
            Categoria("Verduras", listOf("Tomate", "Palta", "Papa", "Zapallo", "Cebolla", "Zanahoria", "Ajo")),
            Categoria("Aliños", listOf("Sal", "Orégano", "Comino", "Pimienta", "Merkén")),
            Categoria("Abarrotes", listOf("Arroz", "Fideos", "Harina", "Pan", "Azúcar", "Aceite"))
        )

        val adapter = MenuAdapter(categorias)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.adapter = adapter

        btn_atras.setOnClickListener {
            finish()
        }

        btn_verificar.setOnClickListener {
            val seleccionMap = adapter.getSeleccion()
            val listaSeleccion = ArrayList<String>()

            // Convertimos el Map<String, List<String>> a una lista simple
            seleccionMap.forEach { (_, items) ->
                listaSeleccion.addAll(items)
            }

            val intent = Intent(this, m_recetas::class.java)
            intent.putStringArrayListExtra("seleccion", listaSeleccion)
            startActivity(intent)
        }
    }
}
