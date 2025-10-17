package com.example.elqh.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elqh.R
import com.example.elqh.models.Categoria

class MenuAdapter(
    private val categorias: List<Categoria>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val seleccionMultiple = mutableMapOf<Int, MutableSet<Int>>()

    companion object {
        private const val TIPO_CATEGORIA = 0
        private const val TIPO_OPCION = 1
    }

    override fun getItemViewType(position: Int): Int {
        var count = 0
        categorias.forEach { cat ->
            if (position == count) return TIPO_CATEGORIA
            count++
            if (position < count + cat.opciones.size) return TIPO_OPCION
            count += cat.opciones.size
        }
        return TIPO_CATEGORIA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TIPO_CATEGORIA) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_categoria, parent, false)
            CategoriaViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_opcion, parent, false)
            OpcionViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var count = 0
        categorias.forEachIndexed { catIndex, categoria ->
            if (position == count && holder is CategoriaViewHolder) {
                holder.bind(categoria.nombre)
                return
            }
            count++
            categoria.opciones.forEachIndexed { optIndex, opcion ->
                if (position == count && holder is OpcionViewHolder) {
                    holder.bind(catIndex, optIndex, opcion)
                    return
                }
                count++
            }
        }
    }

    override fun getItemCount(): Int {
        return categorias.sumOf { it.opciones.size + 1 }
    }

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nombre: String) {
            itemView.findViewById<TextView>(R.id.tvCategoria).text = nombre
        }
    }

    inner class OpcionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val check = itemView.findViewById<CheckBox>(R.id.checkOpcion)

        fun bind(catIndex: Int, optIndex: Int, texto: String) {
            check.text = texto
            val seleccionados = seleccionMultiple.getOrPut(catIndex) { mutableSetOf() }
            check.isChecked = seleccionados.contains(optIndex)

            check.setOnClickListener {
                if (check.isChecked) seleccionados.add(optIndex)
                else seleccionados.remove(optIndex)
            }
        }
    }

    fun getSeleccion(): Map<String, List<String>> {
        val resultado = mutableMapOf<String, List<String>>()
        categorias.forEachIndexed { catIndex, categoria ->
            seleccionMultiple[catIndex]?.let { indices ->
                resultado[categoria.nombre] = indices.map { categoria.opciones[it] }
            }
        }
        return resultado
    }
}
