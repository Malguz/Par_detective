package com.example.detectiveapp.room

import androidx.room3.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {

    @Insert
    suspend fun insertCase(case: Case)

    @Update
    suspend fun updateCase(case: Case)

    @Delete
    suspend fun deleteCase(case: Case)

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