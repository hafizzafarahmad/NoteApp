package com.summersoft.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.summersoft.noteapp.feature_note.domain.model.Note
import com.summersoft.noteapp.feature_note.domain.use_case.NoteMainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteMainUseCase: NoteMainUseCase
): ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentDeleteNote: Note? = null

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.Order -> {

            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteMainUseCase.deleteNoteUseCase(event.note)
                    recentDeleteNote = event.note
                }
            }
            is NoteEvent.RestoreNote -> {

                viewModelScope.launch {
                    noteMainUseCase.addNoteUseCase(recentDeleteNote ?: return@launch)
                    recentDeleteNote = null
                }

            }
            is NoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

}