package com.example.detectiveapp.logic

import com.example.detectiveapp.room.Case
import com.example.detectiveapp.room.CaseDao
import kotlinx.coroutines.flow.Flow

sealed class SaveResult {
    object Success : SaveResult()
    data class Error(val errors: List<String>) : SaveResult()
}

class CaseRepository(private val caseDao: CaseDao) {

    fun getAllCases(): Flow<List<Case>> = caseDao.getAllCases()

    fun search(query: String): Flow<List<Case>> = caseDao.searchCases(query)

    suspend fun getById(id: Int): Case? = caseDao.getCaseById(id)

    suspend fun create(title: String, description: String, date: String, status: String): SaveResult {
        val errors = CaseValidator.validate(title, description, date, status)
        if (errors.isNotEmpty()) return SaveResult.Error(errors)

        caseDao.insertCase(Case(title = title, description = description, date = date, status = status))
        return SaveResult.Success
    }

    suspend fun update(case: Case): SaveResult {
        val errors = CaseValidator.validate(case.title, case.description, case.date, case.status)
        if (errors.isNotEmpty()) return SaveResult.Error(errors)

        caseDao.updateCase(case)
        return SaveResult.Success
    }

    suspend fun delete(case: Case) = caseDao.deleteCase(case)

    suspend fun closeCase(case: Case) {
        caseDao.updateCase(case.copy(status = CaseValidator.closeCase(case.status)))
    }
}