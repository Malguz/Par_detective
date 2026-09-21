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
        val database = AppDatabase.getDatabase(context)
        val caseRepo = CaseRepository(database.caseDao())
        val noteRepo = NoteRepository(database.noteDao())
        val evidenceRepo = EvidenceRepository(database.evidenceDao())

        @Suppress("UNCHECKED_CAST")
        return CaseDetailViewModel(
            caseId = caseId,
            caseRepository = caseRepo,
            noteRepository = noteRepo,
            evidenceRepository = evidenceRepo
        ) as T
    }
}