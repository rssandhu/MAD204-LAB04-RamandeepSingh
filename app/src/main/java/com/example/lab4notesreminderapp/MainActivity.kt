package com.example.lab4notesreminderapp

/**
 * Course: XXX-XXX Lab 4
 * Author: Your Name - StudentID
 * Date: 01-Dec-2025
 * Description: Main Activity for Notes + Reminder app. Handles UI for CRUD notes and starting reminder service.
 */

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

/**
 * MainActivity presenting UI for adding, viewing, editing, and deleting notes;
 * also starts ReminderService and handles Undo operations with Snackbar.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    // View references
    private lateinit var rvNotes: RecyclerView
    private lateinit var btnAddNote: Button
    private lateinit var btnStartReminder: Button
    private lateinit var etTitle: TextInputEditText
    private lateinit var etContent: TextInputEditText

    private var recentlyDeletedNote: Note? = null

    /**
     * Called when activity is first created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views using findViewById (no synthetics)
        rvNotes = findViewById(R.id.rvNotes)
        btnAddNote = findViewById(R.id.btnAddNote)
        btnStartReminder = findViewById(R.id.btnStartReminder)
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)

        // Set up RecyclerView
        adapter = NotesAdapter()
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.setHasFixedSize(true)
        rvNotes.adapter = adapter

        // Set up ViewModel
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        notesViewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }

        // Add note button
        btnAddNote.setOnClickListener {
            addNote()
        }

        // Start reminder service button
        btnStartReminder.setOnClickListener {
            val intent = Intent(this, ReminderService::class.java)
            startService(intent)
            Toast.makeText(this, "Reminder will pop in 5 seconds.", Toast.LENGTH_SHORT).show()
        }

        // Click: edit note
        adapter.onNoteClick = { note ->
            showEditDialog(note)
        }

        // Long click: delete with undo
        adapter.onNoteLongClick = { note, _ ->
            recentlyDeletedNote = note
            notesViewModel.delete(note)
            Snackbar.make(rvNotes, "Note deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    recentlyDeletedNote?.let { n ->
                        notesViewModel.insert(n)
                    }
                }
                .show()
        }
    }

    /**
     * Validates form and inserts a new note.
     */
    private fun addNote() {
        val title = etTitle.text?.toString()?.trim() ?: ""
        val content = etContent.text?.toString()?.trim() ?: ""

        if (TextUtils.isEmpty(title)) {
            etTitle.error = "Title required"
            return
        }
        if (TextUtils.isEmpty(content)) {
            etContent.error = "Content required"
            return
        }

        val newNote = Note(title = title, content = content)
        notesViewModel.insert(newNote)

        etTitle.text?.clear()
        etContent.text?.clear()
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows dialog to edit an existing note.
     * @param note Note selected for editing.
     */
    private fun showEditDialog(note: Note) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null)
        val etEditTitle = dialogView.findViewById<EditText>(R.id.etEditTitle)
        val etEditContent = dialogView.findViewById<EditText>(R.id.etEditContent)

        etEditTitle.setText(note.title)
        etEditContent.setText(note.content)

        AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newTitle = etEditTitle.text.toString().trim()
                val newContent = etEditContent.text.toString().trim()

                if (newTitle.isEmpty() || newContent.isEmpty()) {
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    note.title = newTitle
                    note.content = newContent
                    notesViewModel.update(note)
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
