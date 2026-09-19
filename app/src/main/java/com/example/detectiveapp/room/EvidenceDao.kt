package com.example.detectiveapp.room

import androidx.room3.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenceDao {

    @Insert
    suspend fun insertEvidence(evidence: Evidence)

    @Update
    suspend fun updateEvidence(evidence: Evidence)

    @Delete
    suspend fun deleteEvidence(evidence: Evidence)

    @Query("SELECT * FROM evidence WHERE id_case = :caseId")
    fun getEvidenceByCase(caseId: Int): Flow<List<Evidence>>
}