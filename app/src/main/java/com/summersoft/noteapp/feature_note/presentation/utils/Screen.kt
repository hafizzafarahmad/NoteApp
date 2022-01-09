package com.summersoft.noteapp.feature_note.presentation.utils

sealed class Screen(val route: String){
    object NoteScreen: Screen("notes_screen")
    object AddEditScreen: Screen("add_edit_note_screen")
}