package com.techgv.notesappwithmultipledb.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techgv.domain.entity.NoteEntity

@Composable
fun ItemView(note: NoteEntity, onClick: (String?) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick(note.id)
            },
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = note.note, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
