package com.example.lab4notesreminderapp

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DAO interface providing CRUD operations for Note entity.
 */
@Dao
interface NoteDao {

    /**
     * Insert a new note into the database.
     * @param note Note to insert.
     */
    @Insert
    suspend fun insert(note: Note)

    /**
     * Retrieve all notes sorted by descending ID.
     * @return LiveData list of Notes.
     */
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    /**
     * Update an existing note.
     * @param note Note to update.
     */
    @Update
    suspend fun update(note: Note)

    /**
     * Delete a specific note.
     * @param note Note to delete.
     */
    @Delete
    suspend fun delete(note: Note)
}
