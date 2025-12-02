package com.example.lab4notesreminderapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * RoomDatabase class providing singleton NotesDatabase instance.
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    /**
     * Provides NoteDao for database operations.
     */
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        /**
         * Returns singleton instance of NotesDatabase.
         * @param context Application context.
         * @return NotesDatabase instance.
         */
        fun getInstance(context: Context): NotesDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_db"
                ).build()
                INSTANCE = instance
                instance
            }
    }
}
