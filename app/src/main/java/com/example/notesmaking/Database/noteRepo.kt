package com.example.notesmaking.Database

import androidx.lifecycle.LiveData
import com.example.notesmaking.Models.Note

class noteRepo(private val notedao: NoteDao) {
    val allNotes: LiveData<List<Note>> = notedao.getallnotes()
    suspend fun insert(note: Note) {
        notedao.insert(note)
    }

    suspend fun delete(note: Note) {
        notedao.delete(note)
    }

    suspend fun update(note: Note) {
        notedao.update(note.id, note.title, note.note)
    }
}