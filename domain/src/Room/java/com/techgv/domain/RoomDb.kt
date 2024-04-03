package com.techgv.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.techgv.domain.dao.NoteDao
import com.techgv.domain.entity.RoomNoteEntity

@Database(entities = [RoomNoteEntity::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private lateinit var INSTANCE: RoomDb


        fun getAppDatabase(context: Context): RoomDb {
            if (!::INSTANCE.isInitialized) {
                INSTANCE =
                    Room.databaseBuilder(context.applicationContext, RoomDb::class.java, "AppDB")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }
    }
}