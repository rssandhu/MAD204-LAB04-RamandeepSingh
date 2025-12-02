package com.example.lab4notesreminderapp

/**
 * Course: XXX-XXX Lab 4
 * Author: Your Name - StudentID
 * Date: 01-Dec-2025
 * Description: Entity class representing one Note in Room database.
 */
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var content: String
)