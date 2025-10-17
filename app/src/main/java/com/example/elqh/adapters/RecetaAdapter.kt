package com.example.elqh.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elqh.R
import com.example.elqh.models.Receta

class RecetaAdapter(
    private val recetas: List<Receta>,
    private val onClick: (Receta) -> Unit
) : RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder>() {

    inner class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.imgReceta)
        private val nombre = itemView.findViewById<TextView>(R.id.tvNombreReceta)
        private val ingredientes = itemView.findViewById<TextView>(R.id.tvIngredientesReceta)

        fun bind(receta: Receta) {
            img.setImageResource(receta.imagen)
            nombre.text = receta.nombre
            ingredientes.text = receta.ingredientes.joinToString(", ")
            itemView.setOnClickListener { onClick(receta) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        holder.bind(recetas[position])
    }

    override fun getItemCount(): Int = recetas.size
}