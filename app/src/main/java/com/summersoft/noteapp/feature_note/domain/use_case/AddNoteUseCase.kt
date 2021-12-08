package com.summersoft.noteapp.feature_note.domain.use_case

import com.summersoft.noteapp.feature_note.domain.model.InvalidNoteException
import com.summersoft.noteapp.feature_note.domain.model.Note
import com.summersoft.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){

        if(note.title.isBlank()){
            throw InvalidNoteException(message = "Tittle cannot be blank.")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException(message = "Content cannot be blank.")
        }
        noteRepository.insertNote(note = note)
    }
}