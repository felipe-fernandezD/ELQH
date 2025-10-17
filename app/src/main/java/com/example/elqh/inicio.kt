package com.example.elqh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //elementos de la paguina
        val btn_ingrediente= findViewById<Button>(R.id.btn_ingred)
        val btn_recetas= findViewById<Button>(R.id.btn_recetas)
        val btn_perfil= findViewById<Button>(R.id.btn_perfil)

        btn_recetas.setOnClickListener {
            val intent = Intent(this, m_recetas::class.java)
            startActivity(intent)
        }
        btn_ingrediente.setOnClickListener {
            val intent = Intent(this, ingrdientes::class.java)
            startActivity(intent)
        }
        btn_perfil.setOnClickListener {
            val intent = Intent(this, perfil::class.java)
            startActivity(intent)
        }
        }
    }
