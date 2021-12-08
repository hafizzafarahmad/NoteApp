package com.summersoft.noteapp.feature_note.domain.use_case

data class NoteMainUseCase(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase
)