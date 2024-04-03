package com.techgv.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.techgv.domain.entity.RoomNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note:RoomNoteEntity)

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNote(id:Int)

    @Query("SELECT * FROM Note")
    fun getAllNote(): Flow<List<RoomNoteEntity>>

    @Update
    suspend fun updateNote(note: RoomNoteEntity)

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id:Int): RoomNoteEntity
}