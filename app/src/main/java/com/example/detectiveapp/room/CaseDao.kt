package com.example.detectiveapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {

    @Insert
    suspend fun insertCase(caseItem: Case)

    @Update
    suspend fun updateCase(caseItem: Case)

    @Delete
    suspend fun deleteCase(caseItem: Case)

    @Query("SELECT * FROM cases ORDER BY id DESC")
    fun getAllCases(): Flow<List<Case>>

    @Query("SELECT * FROM cases WHERE id = :id")
    suspend fun getCaseById(id: Int): Case?

    @Query("""
        SELECT * FROM cases 
        WHERE title LIKE '%' || :query || '%'
        OR status LIKE '%' || :query || '%'
    """)
    fun searchCases(query: String): Flow<List<Case>>
}