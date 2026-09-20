package com.example.detectiveapp.logic

import com.example.detectiveapp.room.Evidence
import com.example.detectiveapp.room.EvidenceDao
import kotlinx.coroutines.flow.Flow

class EvidenceRepository(private val evidenceDao: EvidenceDao) {
    fun getByCase(caseId: Int): Flow<List<Evidence>> = evidenceDao.getEvidenceByCase(caseId)

    suspend fun add(caseId: Int, collectionDate: String, description: String, location: String, importance: String) {
        require(description.isNotBlank()) { "La descripción de la evidencia no puede estar vacía." }
        evidenceDao.insertEvidence(
            Evidence(id_case = caseId, collection_date = collectionDate, description = description, location = location, importance = importance)
        )
    }

    suspend fun delete(evidence: Evidence) = evidenceDao.deleteEvidence(evidence)
}