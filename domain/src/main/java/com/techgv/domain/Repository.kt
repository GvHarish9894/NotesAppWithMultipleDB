package com.techgv.domain

import com.techgv.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addData(note: NoteEntity?)
    suspend fun getData(): Flow<List<NoteEntity>>
    suspend fun deleteData(id: NoteEntity?)
    suspend fun updateData(note: NoteEntity?)
    suspend fun getNoteById(id: String): Flow<NoteEntity?>
}