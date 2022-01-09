package com.summersoft.noteapp.feature_note.presentation.list_notes

import com.summersoft.noteapp.feature_note.domain.model.Note
import com.summersoft.noteapp.feature_note.domain.utils.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val  note: Note): NoteEvent()
    object RestoreNote: NoteEvent()
    object ToggleOrderSection: NoteEvent()
}