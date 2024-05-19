package com.techgv.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Image")
class ImageTest {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 1

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var data: ByteArray? = null
}