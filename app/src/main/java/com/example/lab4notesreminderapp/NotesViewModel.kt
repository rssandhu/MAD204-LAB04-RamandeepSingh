package com.example.lab4notesreminderapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel for managing Notes data for UI lifecycle awareness.
 */
class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NotesRepository(application)

    /**
     * LiveData observing all notes.
     */
    val allNotes: LiveData<List<Note>> = repository.allNotes

    /**
     * Insert a note in background.
     * @param note Note to insert.
     */
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    /**
     * Update a note in background.
     * @param note Note to update.
     */
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    /**
     * Delete a note in background.
     * @param note Note to delete.
     */
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}
