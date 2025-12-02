package com.example.lab4notesreminderapp

import android.app.Application
import androidx.lifecycle.LiveData

/**
 * Repository layer to abstract and manage data operations.
 */
class NotesRepository(application: Application) {

    private val noteDao = NotesDatabase.getInstance(application).noteDao()
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    /**
     * Insert note asynchronously.
     * @param note Note to insert.
     */
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    /**
     * Update note asynchronously.
     * @param note Note to update.
     */
    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    /**
     * Delete note asynchronously.
     * @param note Note to delete.
     */
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}
