package com.example.jardines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jardines.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val titulo = intent.getStringExtra("titulo")
        val calle = intent.getStringExtra("calle")
        val imagen = intent.getStringExtra("imagen")
        val lastUpdated = intent.getStringExtra("lastUpdated")

        binding.tvDetNombre.text = titulo ?: "Sin nombre"
binding.tvDetDireccion.text = "📍 ${calle ?: "Sin dirección"}"
binding.tvDetFechaActualizacion.text = "🕐 Actualizado: ${lastUpdated ?: "-"}"

       if (!imagen.isNullOrEmpty()) {
    val urlCompleta = if (imagen.startsWith("//")) "https:$imagen" else imagen
    Picasso.get()
        .load(urlCompleta)
        .placeholder(android.R.drawable.ic_menu_gallery)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .into(binding.ivDetImagen)
} else {
    binding.ivDetImagen.setImageResource(android.R.drawable.ic_menu_gallery)
}

        // Botón volver
        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}
