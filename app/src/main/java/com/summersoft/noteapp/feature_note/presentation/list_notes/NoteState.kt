package com.summersoft.noteapp.feature_note.presentation.list_notes

import com.summersoft.noteapp.feature_note.domain.model.Note
import com.summersoft.noteapp.feature_note.domain.utils.NoteOrder
import com.summersoft.noteapp.feature_note.domain.utils.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)