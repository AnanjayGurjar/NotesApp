package android.example.advancednoteskotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val context: Context,
                  val onClick: OnClick): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_noteTitle = itemView.findViewById<TextView>(R.id.tv_noteTitle)
        val tv_time = itemView.findViewById<TextView>(R.id.tv_timeStamp)
        val iv_deleteNote = itemView.findViewById<ImageView>(R.id.iv_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.tv_noteTitle.setText(allNotes.get(position).noteTitle)
        holder.tv_time.setText("Last Updated: ${allNotes.get(position).timestamp}")

        holder.iv_deleteNote.setOnClickListener {
            onClick.onDeleteIconClick(allNotes.get(position))
        }
        holder.itemView.setOnClickListener {
            onClick.onNoteClick(allNotes.get(position))
        }

    }

    override fun getItemCount(): Int {
    return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface OnClick{
    fun onDeleteIconClick(note: Note)
    fun onNoteClick(note:Note)
}
