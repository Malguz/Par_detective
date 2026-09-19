package com.example.detectiveapp.room


import androidx.room3.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client): Long

    @Update
    suspend fun updateClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("SELECT * FROM client ORDER BY name ASC")
    fun getAllClients(): Flow<List<Client>>

    @Query("SELECT * FROM client WHERE id = :clientId")
    suspend fun getClientById(clientId: Int): Client?

    @Query("SELECT * FROM client WHERE id_case = :caseId")
    fun getClientsByCaseId(caseId: Int): Flow<List<Client>>

    @Query("SELECT * FROM client WHERE name LIKE '%' || :query || '%'")
    fun searchClientsByName(query: String): Flow<List<Client>>
}