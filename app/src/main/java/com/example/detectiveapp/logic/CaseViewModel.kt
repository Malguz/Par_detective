package com.example.detectiveapp.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detectiveapp.room.Case
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CaseViewModel(private val repository: CaseRepository) : ViewModel() {

    private val _errorMessage = MutableStateFlow<List<String>>(emptyList())
    val errorMessage: StateFlow<List<String>> = _errorMessage

    val cases: StateFlow<List<Case>> = repository.getAllCases()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun search(query: String): StateFlow<List<Case>> {
        return repository.search(query)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun createCase(title: String, description: String, date: String, status: String) {
        viewModelScope.launch {
            when (val result = repository.create(title, description, date, status)) {
                is SaveResult.Error -> _errorMessage.value = result.errors
                is SaveResult.Success -> _errorMessage.value = emptyList()
            }
        }
    }

    fun updateCase(case: Case) {
        viewModelScope.launch {
            when (val result = repository.update(case)) {
                is SaveResult.Error -> _errorMessage.value = result.errors
                is SaveResult.Success -> _errorMessage.value = emptyList()
            }
        }
    }

    fun deleteCase(case: Case) {
        viewModelScope.launch { repository.delete(case) }
    }
}