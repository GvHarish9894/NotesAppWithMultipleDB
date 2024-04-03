package com.techgv.domain

import android.content.Context
import com.techgv.domain.entity.NoteEntity
import com.techgv.domain.entity.RoomNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow


class NoteRepositoryImpl(context: Context) : NoteRepository {

    private var database: RoomDb = RoomDb.getAppDatabase(context)

    override suspend fun addData(note: NoteEntity?) {
        note?.let {
            database.noteDao().addNote(note.toRoomNoteEntity())
        }
    }

    override suspend fun getData(): Flow<List<NoteEntity>> {
        return flow {
            database.noteDao().getAllNote().distinctUntilChanged().collect { roomEntity ->
                emit(roomEntity.map { it.toNoteEntity() })
            }
        }
    }

    override suspend fun deleteData(note: NoteEntity?) {
        note?.let {
            database.noteDao().deleteNote(it.id?.toInt() ?: -1)
        }
    }

    override suspend fun updateData(note: NoteEntity?) {
        note?.let {
            database.noteDao().updateNote(note.toRoomNoteEntity())
        }
    }

    override suspend fun getNoteById(id: String): Flow<NoteEntity?> {
        return flow {
            database.noteDao().getNoteById(id.toInt()).let { roomEntity ->
                emit(roomEntity.toNoteEntity())
            }
        }
    }

}

fun NoteEntity.toRoomNoteEntity(): RoomNoteEntity {
    return RoomNoteEntity(
        id = this.id?.toInt(),
        note = this.note
    )
}

fun RoomNoteEntity.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = this.id.toString(),
        note = this.note
    )
}