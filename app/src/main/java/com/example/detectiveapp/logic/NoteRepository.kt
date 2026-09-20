package com.example.detectiveapp.logic

import com.example.detectiveapp.room.Note
import com.example.detectiveapp.room.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    fun obtenerPorCaso(caseId: Int): Flow<List<Note>> = noteDao.getNotesByCase(caseId)

    suspend fun agregar(caseId: Int, descripcion: String, fecha: String) {
        require(descripcion.isNotBlank()) { "El hallazgo no puede estar vacío." }
        noteDao.insertNote(Note(id_case = caseId, description = descripcion, date = fecha))
    }

    suspend fun eliminar(nota: Note) = noteDao.deleteNote(nota)
}