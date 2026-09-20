package com.example.detectiveapp.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.detectiveapp.room.AppDatabase

class CaseViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getDatabase(context).caseDao()
        val repository = CaseRepository(dao)
        @Suppress("UNCHECKED_CAST")
        return CaseViewModel(repository) as T
    }
}