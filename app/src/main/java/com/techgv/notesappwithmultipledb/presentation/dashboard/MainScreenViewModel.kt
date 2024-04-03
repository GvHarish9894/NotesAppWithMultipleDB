package com.techgv.notesappwithmultipledb.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgv.domain.NoteRepository
import com.techgv.domain.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repositoryImpl: NoteRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getNotesList()
    }

    private fun getNotesList() {
        viewModelScope.launch {
             repositoryImpl.getData().collect { result ->
                 _uiState.update {
                     it.copy(notes = result)
                 }
             }
        }
    }

    data class UiState(
        val notes: List<NoteEntity> = emptyList()
    )
}