package com.example.detectiveapp.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detectiveapp.room.Case
import com.example.detectiveapp.room.Evidence
import com.example.detectiveapp.room.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CaseDetailViewModel(
    private val caseId: Int,
    private val caseRepository: CaseRepository,
    private val noteRepository: NoteRepository,
    private val evidenceRepository: EvidenceRepository
) : ViewModel() {

    val findings: StateFlow<List<Note>> = noteRepository.getByCase(caseId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val evidence: StateFlow<List<Evidence>> = evidenceRepository.getByCase(caseId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addFinding(description: String, date: String) {
        viewModelScope.launch {
            noteRepository.add(caseId, description, date)
        }
    }

    fun addEvidence(collectionDate: String, description: String, location: String, importance: String) {
        viewModelScope.launch {
            evidenceRepository.add(caseId, collectionDate, description, location, importance)
        }
    }

    fun closeCase(case: Case) {
        viewModelScope.launch {
            caseRepository.closeCase(case)
        }
    }
}