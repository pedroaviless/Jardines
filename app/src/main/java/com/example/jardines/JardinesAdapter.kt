package com.example.jardines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jardines.databinding.ItemJardinesBinding
import com.example.jardines.model.Parque
import com.squareup.picasso.Picasso

class JardinesAdapter(
    private val onItemClick: (Parque) -> Unit
) : RecyclerView.Adapter<JardinesAdapter.JardinesViewHolder>() {

    private var listaCompleta: List<Parque> = emptyList()
    private var listaFiltrada: List<Parque> = emptyList()

    fun setData(lista: List<Parque>) {
        listaCompleta = lista
        listaFiltrada = lista
        notifyDataSetChanged()
    }

    fun filtrar(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listaCompleta
        } else {
            listaCompleta.filter { parque ->
                parque.titulo.contains(texto, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    inner class JardinesViewHolder(
        private val binding: ItemJardinesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(parque: Parque) {
            binding.tvJardines.text = parque.titulo.ifEmpty { "Sin nombre" }
            binding.tvCalle.text = parque.calle.ifEmpty { "Sin nombre" }

           if (parque.imagen.isNotEmpty()) {
    val urlCompleta = if (parque.imagen.startsWith("//")) "https:${parque.imagen}" else parque.imagen
    Picasso.get()
        .load(urlCompleta)
        .placeholder(android.R.drawable.ic_menu_gallery)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .fit()
        .centerCrop()
        .into(binding.ivJardines)
} else {
    binding.ivJardines.setImageResource(android.R.drawable.ic_menu_gallery)
}
            binding.cardJardines.setOnClickListener {
                onItemClick(parque)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JardinesViewHolder {
        val binding = ItemJardinesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return JardinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JardinesViewHolder, position: Int) {
        holder.bind(listaFiltrada[position])
    }

    override fun getItemCount(): Int {
        return listaFiltrada.size
    }
}
