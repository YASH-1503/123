package com.example.notesmaking

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesmaking.Models.Note
import com.example.notesmaking.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class add_note : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(old_note.title)
            binding.etNotes.setText(old_note.note)
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.check.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNotes.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                val currentDate = formatter.format(Date())

                if (isUpdate) {
                    note = Note(old_note.id, title, note_desc, currentDate)
                } else {
                    note = Note(null, title, note_desc, currentDate)
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this@add_note, "Please add something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.backarrow.setOnClickListener {
            onBackPressed()
        }
    }
}
