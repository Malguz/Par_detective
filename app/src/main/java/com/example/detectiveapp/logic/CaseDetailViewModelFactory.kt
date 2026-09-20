package com.example.detectiveapp.logic


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.detectiveapp.room.AppDatabase

class CaseDetailViewModelFactory(
    private val context: Context,
    private val caseId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = AppDatabase.getDatabase(context)
        val caseRepository = CaseRepository(db.caseDao())
        val noteRepository = NoteRepository(db.noteDao())
        val evidenceRepository = EvidenceRepository(db.evidenceDao())
        @Suppress("UNCHECKED_CAST")
        return CaseDetailViewModel(caseId, caseRepository, noteRepository, evidenceRepository) as T
    }
}