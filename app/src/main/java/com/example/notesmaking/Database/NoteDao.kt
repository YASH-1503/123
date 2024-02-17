package com.example.notesmaking.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesmaking.Models.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notes_table Order by id ASC")
    fun getallnotes(): LiveData<List<Note>>

    @Query("Update notes_table set title=:title ,note=:note where id=:id ")
    suspend fun update(id: kotlin.Long?, title: String?, note: String?)
}