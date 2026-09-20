package com.example.detectiveapp.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detectiveapp.room.cases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CaseViewModel(private val repository: CaseRepository) : ViewModel() {

    private val _mensajeError = MutableStateFlow<List<String>>(emptyList())
    val mensajeError: StateFlow<List<String>> = _mensajeError

    val casos: StateFlow<List<cases>> = repository.obtenerTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun buscar(query: String): StateFlow<List<cases>> {
        return repository.buscar(query)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun crearCaso(titulo: String, descripcion: String, fecha: String, estado: String) {
        viewModelScope.launch {
            when (val resultado = repository.crear(titulo, descripcion, fecha, estado)) {
                is ResultadoGuardado.Error -> _mensajeError.value = resultado.errores
                is ResultadoGuardado.Exito -> _mensajeError.value = emptyList()
            }
        }
    }

    fun actualizarCaso(caso: cases) {
        viewModelScope.launch {
            when (val resultado = repository.actualizar(caso)) {
                is ResultadoGuardado.Error -> _mensajeError.value = resultado.errores
                is ResultadoGuardado.Exito -> _mensajeError.value = emptyList()
            }
        }
    }

    fun eliminarCaso(caso: cases) {
        viewModelScope.launch { repository.eliminar(caso) }
    }
}