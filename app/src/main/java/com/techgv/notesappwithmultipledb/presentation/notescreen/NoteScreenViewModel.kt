package com.techgv.notesappwithmultipledb.presentation.notescreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgv.domain.NoteRepository
import com.techgv.domain.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel @Inject constructor(private val repository: NoteRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UiData())
    val uiState = _uiState.asStateFlow()

    private var noteEntity: NoteEntity? = null


    fun updateNote() {
        viewModelScope.launch {
            if (noteEntity != null && noteEntity?.id != null && uiState.value.note?.note?.isEmpty() == true || uiState.value.note?.note?.isBlank() == true) {
                repository.deleteData(uiState.value.note)
            } else if (noteEntity != null && uiState.value.note?.note?.isNotEmpty() == true && uiState.value.note?.note?.isNotBlank() == true) {
                repository.updateData(uiState.value.note)
            } else {
                repository.addData(uiState.value.note)
            }
        }
    }

    fun getNote(noteId: String?) {
        noteId?.let {
            viewModelScope.launch {
                repository.getNoteById(it).collect { note ->
                    noteEntity = note
                    _uiState.update {
                        it.copy(note = note)
                    }
                }
            }
        }
    }


    fun setText(text: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(note = it.note?.copy(note = text))
            }
        }
    }

    data class UiData(
        val note: NoteEntity? = NoteEntity()
    )
}