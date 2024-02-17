package com.example.notesmaking

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesmaking.Adapter.NoteAdapter
import com.example.notesmaking.Database.NoteDatabase
import com.example.notesmaking.Models.Note
import com.example.notesmaking.Models.NoteViewModal
import com.example.notesmaking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteAdapter.Noteclicklistner,
    PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModal
    lateinit var adapter: NoteAdapter
    lateinit var selectedNote: Note

    private val updateNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as? Note
                if (note != null) {
                    viewModel.updateNote(note)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
            database = NoteDatabase.getdatabase(this)
        }
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = NoteAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val note = result.data?.getSerializableExtra("note") as? Note
                    note?.let {
                        viewModel.insertNote(it)
                    }
                }
            }
        binding.fbAddNote.setOnClickListener {
            val intent = Intent(this, add_note::class.java)
            getContent.launch(intent)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filterlist(newText)
                }
                return true
            }
        })

    }

    override fun Onitemclick(note: Note) {
        val intent = Intent(this@MainActivity, add_note::class.java)
        intent.putExtra("current_note", note)
        updateNote.launch(intent)
    }

    override fun onLongtermclicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpdisplay(cardView)
    }

    private fun popUpdisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note) {
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}


