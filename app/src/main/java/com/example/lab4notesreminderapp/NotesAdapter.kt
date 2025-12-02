package com.example.lab4notesreminderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView adapter for displaying a list of notes.
 */
class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteHolder>(DIFF_CALLBACK) {

    var onNoteClick: ((Note) -> Unit)? = null
    var onNoteLongClick: ((Note, Int) -> Unit)? = null

    companion object {
        /**
         * DiffUtil for optimizing list updates.
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note) =
                oldItem.title == newItem.title && oldItem.content == newItem.content
        }
    }

    /**
     * ViewHolder class holding note item views.
     */
    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvItemContent)

        init {
            // Click triggers note edit.
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) onNoteClick?.invoke(getItem(position))
            }
            // Long click triggers deletion.
            itemView.setOnLongClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onNoteLongClick?.invoke(getItem(position), position)
                    true
                } else false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val current = getItem(position)
        holder.tvTitle.text = current.title
        holder.tvContent.text = current.content
    }
}
