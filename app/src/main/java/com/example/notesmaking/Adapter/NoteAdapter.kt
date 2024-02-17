package com.example.notesmaking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmaking.Models.Note
import com.example.notesmaking.R
import kotlin.random.Random

class NoteAdapter(private val context: Context, val Listner: Noteclicklistner) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val NoteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)
        NoteList.clear()
        NoteList.addAll(fullList)
        notifyDataSetChanged()

    }

    fun filterlist(search: String) {
        NoteList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            )
                NoteList.add(item)
        }
        notifyDataSetChanged()
    }

    fun randomcolor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)
        val seed = System.currentTimeMillis().toInt()
        val randomindex = Random(seed).nextInt(list.size)
        return list[randomindex]

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NoteList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.date.text = currentNote.date
        holder.date.isSelected = true
        holder.note_tv.text = currentNote.note
        holder.notelayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomcolor(),
                null
            )
        )
        holder.notelayout.setOnClickListener {
            Listner.Onitemclick(NoteList[holder.adapterPosition])
        }
        holder.notelayout.setOnLongClickListener {
            Listner.onLongtermclicked(NoteList[holder.adapterPosition], holder.notelayout);true
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notelayout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
        val note_tv = itemView.findViewById<TextView>(R.id.tv_notes)
    }

    interface Noteclicklistner {
        fun Onitemclick(note: Note)
        fun onLongtermclicked(note: Note, cardView: CardView)
    }
}
