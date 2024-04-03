package com.techgv.domain.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmNoteEntity : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var note: String = ""
}


