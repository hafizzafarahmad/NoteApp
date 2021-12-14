package com.summersoft.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.summersoft.noteapp.feature_note.domain.model.Note
import com.summersoft.noteapp.feature_note.domain.use_case.NoteMainUseCase
import com.summersoft.noteapp.feature_note.domain.utils.NoteOrder
import com.summersoft.noteapp.feature_note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteMainUseCase: NoteMainUseCase
): ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentDeleteNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class &&
                        state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }

                getNotes(event.noteOrder)

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

    fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()

        getNotesJob = noteMainUseCase.getNotesUseCase(noteOrder).onEach {
            _state.value = state.value.copy(
                notes = it,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

}