package android.example.advancednoteskotlin

import android.content.Intent
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    var noteId = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")

        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")

            noteId = intent.getIntExtra("ID", -1)

            btn_update.setText("Update Note")
            et_noteTitle.setText(noteTitle)
            et_noteDescription.setText(noteDescription)

        }else{
            btn_update.setText("Add Note")


        }
        btn_update.setOnClickListener{
            val noteTitle = et_noteTitle.text.toString()
            val noteDescription = et_noteDescription.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated...", Toast.LENGTH_SHORT).show()
                    startMainActivity()

                }else{
                    Toast.makeText(this, "Above Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }

            }else{
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val newNote = Note(noteTitle, noteDescription, currentDate)

                    viewModel.addNote(newNote)


                    Toast.makeText(this, "Note Added...", Toast.LENGTH_SHORT).show()
                    startMainActivity()
                }else{
                    Toast.makeText(this, "Above Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}