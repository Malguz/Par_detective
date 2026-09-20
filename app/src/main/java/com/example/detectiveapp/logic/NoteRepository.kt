package com.example.detectiveapp.logic

import com.example.detectiveapp.room.Note
import com.example.detectiveapp.room.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    fun getByCase(caseId: Int): Flow<List<Note>> = noteDao.getNotesByCase(caseId)

    suspend fun add(caseId: Int, description: String, date: String) {
        require(description.isNotBlank()) { "El hallazgo no puede estar vacío." }
        noteDao.insertNote(Note(id_case = caseId, description = description, date = date))
    }

    suspend fun delete(note: Note) = noteDao.deleteNote(note)
}