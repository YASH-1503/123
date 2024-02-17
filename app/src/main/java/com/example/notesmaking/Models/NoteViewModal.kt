package com.example.notesmaking.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmaking.Database.NoteDatabase
import com.example.notesmaking.Database.noteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModal(application: Application) : AndroidViewModel(application) {
    private val repository: noteRepo
    val allnotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getdatabase(application).getnotedao()
        repository = noteRepo(dao)
        allnotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

}