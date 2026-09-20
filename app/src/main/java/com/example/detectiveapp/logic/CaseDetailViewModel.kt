package com.example.detectiveapp.logic

import androidx.lifecycle.ViewModel
import com.example.detectiveapp.room.Evidence
import com.example.detectiveapp.room.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class CaseDetailViewModel(
    private val caseId: Int,
    private val caseRepository: CaseRepository,
    private val noteRepository: NoteRepository,
    private val evidenceRepository: EvidenceRepository
    ) :  ViewModel() {

    val hallazgos: StateFlow<List<Note>> = noteRepository.obtenerCaso(caseId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val evidencias: StateFlow<List<Evidence>> = evidenceRepository.obtenerPorCaso(caseId)
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarHallazgo(descripcion: String, fecha: String){
        viewModelScope.launch {
            noteRepository.agregar(caseId, descripcion, fecha)
        }
    }

    fun agregarEvidencia(fechaRecoleccion: String, descripcion: String, ubicacion: String, importancia: String) {
        ViewModelScope.launch{
            evidenceRepository.agregar(caseId, fechaRecoleccion, descripcion,ubicacion, importancia)
        }
    }
    fun cerrarCaso(caso: cases){
        viewModelScope.launch{
            caseRepository.cerrarCaso(caso)
        }
    }
}