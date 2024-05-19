package com.techgv.notesappwithmultipledb.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techgv.notesappwithmultipledb.presentation.dashboard.MainScreen
import com.techgv.notesappwithmultipledb.presentation.notescreen.NoteScreen
import com.techgv.notesappwithmultipledb.ui.theme.NotesAppWithMultipleDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NotesAppWithMultipleDBTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.route,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    composable(Screens.Home.route) {
                        MainScreen(navController = navController)
                    }
                    composable(
                        route = Screens.NoteScreen.route,
                        arguments = listOf(navArgument("noteId") {
                            nullable = true
                            defaultValue = null
                            type = NavType.StringType
                        })
                    ) {
                        NoteScreen(navController = navController, noteId = it.arguments?.getString("noteId",null))
                    }
                }
            }
        }
    }
}

sealed class Screens(val route: String) {
    data object Home : Screens("DashboardScreen")
    data object NoteScreen : Screens("NoteScreen?noteId={noteId}"){
        fun getRoute(noteId: String?=null) ="NoteScreen?noteId=$noteId"
    }
}