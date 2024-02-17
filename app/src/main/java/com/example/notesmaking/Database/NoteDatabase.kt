package com.example.notesmaking.Database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesmaking.Models.Note
import com.example.notesmaking.Utiles.DATABASE_NAME

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getnotedao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getdatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()


                INSTANCE

            }
        }
    }

}