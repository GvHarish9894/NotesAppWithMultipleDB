package com.techgv.domain

import android.content.Context
import com.techgv.domain.entity.NoteEntity
import com.techgv.domain.entity.RealmNoteEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import org.mongodb.kbson.ObjectId

class NoteRepositoryImpl(private val context: Context) : NoteRepository {

    private val realm: Realm


    init {

        val config: RealmConfiguration =
            RealmConfiguration.Builder(schema = setOf(RealmNoteEntity::class))
                .name("note.realm")
                .schemaVersion(1)
                .build()
        realm = Realm.open(config)
    }

    override suspend fun addData(note: NoteEntity?) {
        note?.let {
            realm.writeBlocking {
                copyToRealm(note.toRealmNoteEntity())
            }
        }
    }

    override suspend fun getData(): Flow<List<NoteEntity>> {
        return flow {
            realm.query(RealmNoteEntity::class).find().asFlow().distinctUntilChanged()
                .collect { result ->
                    emit(result.list.map { it.toNoteEntity() })
                }
        }
    }

    override suspend fun deleteData(note: NoteEntity?) {
        note?.let {
            realm.writeBlocking {
                val note = query(RealmNoteEntity::class, "id = $0",ObjectId(it.id ?: "")).first()
                delete(note)
            }
        }
    }

    override suspend fun updateData(note: NoteEntity?) {
        note?.let {
            realm.query(
                clazz = RealmNoteEntity::class,
                query = "id = $0", ObjectId(note.id ?: "")
            ).first().find()?.also {
                realm.writeBlocking {
                    findLatest(it)?.note = note.note
                }
            }
        }
    }

    override suspend fun getNoteById(id: String): Flow<NoteEntity?> {
        return flow {
            val result =
                realm.query(RealmNoteEntity::class, query = "id = $0", ObjectId(id)).first().find()
                    ?.toNoteEntity()
            emit(result)
        }
    }


    private fun NoteEntity.toRealmNoteEntity(): RealmNoteEntity {
        val realmNoteEntity = RealmNoteEntity()
        realmNoteEntity.note = this.note
        if (this.id != null){
            realmNoteEntity.id = ObjectId(this.id)
        }
        return realmNoteEntity
    }

    private fun RealmNoteEntity.toNoteEntity(): NoteEntity {
        return NoteEntity(
            id = this.id.toHexString(),
            note = this.note
        )
    }

    fun close() = realm.close()

}