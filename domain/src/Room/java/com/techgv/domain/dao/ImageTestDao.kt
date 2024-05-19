package com.techgv.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techgv.domain.entity.ImageTest

@Dao
interface ImageTestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByReplacement(image: List<ImageTest>)

    @Query("SELECT * FROM Image")
    fun getAll(): List<ImageTest>

    @Query("SELECT * FROM image WHERE id  = :imageTestIds")
    fun findByIds(imageTestIds: Long): ImageTest

    @Delete
    fun delete(imageTest: ImageTest)
}