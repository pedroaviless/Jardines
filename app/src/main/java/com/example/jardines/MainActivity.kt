package com.example.jardines

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jardines.databinding.ActivityMainBinding
import com.example.jardines.model.Parque
import com.example.jardines.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var adapter: JardinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = JardinesAdapter { parque ->
            abrirDetalle(parque)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filtrar(newText.orEmpty())
                return true
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            cargarDatos()
        }

        cargarDatos()
        }
    private fun cargarDatos(){
        showLoading(true)

        scope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    RetrofitClient.apiService.getParques()
                }

                showParques(response.equipamiento)
            } catch (e: Exception){
                showError("No se pudo cargar los parques")
        } finally {
            showLoading(false)
                binding.swipeRefresh.isRefreshing = false
        }
    }
}
private fun showParques(parques: List<Parque>) {
    if (parques.isEmpty()) {
        showError("No se encontraron parques")
    }
    binding.recyclerView.visibility = View.VISIBLE
    binding.tvError.visibility = View.GONE

    adapter.setData(parques)
}
    private fun showError(mensaje: String) {
        binding.recyclerView.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = mensaje
    }
    private fun showLoading(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
   private fun abrirDetalle(parque: Parque) {
    val intent = Intent(this, DetailActivity::class.java).apply {
        putExtra("titulo",      parque.titulo)
        putExtra("calle",       parque.calle)
        putExtra("imagen",      parque.imagen)
        putExtra("lastUpdated", parque.lastUpdated)
    }
    startActivity(intent)
}

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}


