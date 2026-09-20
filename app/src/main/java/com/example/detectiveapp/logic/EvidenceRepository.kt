package com.example.detectiveapp.logic

import com.example.detectiveapp.room.Evidence
import com.example.detectiveapp.room.EvidenceDao
import kotlinx.coroutines.flow.Flow

class EvidenceRepository(private val evidenceDao: EvidenceDao) {
    fun obtenerPorCaso(caseId: Int): Flow<List<Evidence>> = evidenceDao.getEvidenceByCase(caseId)

    suspend fun agregar(caseId: Int, fechaRecoleccion: String, descripcion: String, ubicacion: String, importancia: String) {
        require(descripcion.isNotBlank()) { "La descripción de la evidencia no puede estar vacía." }
        evidenceDao.insertEvidence(
            Evidence(id_case = caseId, collection_date = fechaRecoleccion, description = descripcion, location = ubicacion, importance = importancia)
        )
    }

    suspend fun eliminar(evidencia: Evidence) = evidenceDao.deleteEvidence(evidencia)
}