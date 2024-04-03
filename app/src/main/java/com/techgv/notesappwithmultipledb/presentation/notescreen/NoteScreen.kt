package com.techgv.notesappwithmultipledb.presentation.notescreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(
    navController: NavHostController,
    viewModel: NoteScreenViewModel = hiltViewModel(),
    noteId: String?
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getNote(noteId)
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Surface(
            color = Color.White,
            shadowElevation = 4.dp,
        ) {
            TopAppBar(
                title = { Text("Write your note") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.updateNote()
                        keyboardController?.hide()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        viewModel.updateNote()
                        keyboardController?.hide()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Done, contentDescription = "Back")
                    }
                }
            )
        }

        TextField(modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester),
            value = state.note?.note ?: "",
            onValueChange = { it -> viewModel.setText(it) })
    }

    BackHandler(enabled = true, onBack = {
        viewModel.updateNote()
        keyboardController?.hide()
        navController.popBackStack()
    })
}
