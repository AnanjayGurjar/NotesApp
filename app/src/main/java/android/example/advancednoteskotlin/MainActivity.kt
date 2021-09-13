package android.example.advancednoteskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClick {
    lateinit var viewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        fb_newNote.setOnClickListener{
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
            this.finish()

        }
        rv_notes.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(this, this)
        rv_notes.adapter = noteAdapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNote.observe(this, Observer { list->
            list?.let{
                noteAdapter.updateList(it)
            }

        })
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("ID", note.id)
        startActivity(intent)
        this.finish()

    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()
    }
}