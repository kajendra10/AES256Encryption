package com.kjprojects.aes_256encryption
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var inputNote: EditText
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button
    private lateinit var decryptedNote: TextView

    private val PREFS_NAME = "secure_notes"
    private val ENCRYPTED_NOTE_KEY = "encrypted_note"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declarations
        inputNote = findViewById(R.id.inputNote)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)
        decryptedNote = findViewById(R.id.decryptedNote)

        //Save button function
        saveButton.setOnClickListener {
            val noteText = inputNote.text.toString()
            if (noteText.isNotEmpty()){
                val encrypted = AES256Helper.encrypt(noteText)
                val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                prefs.edit { putString(ENCRYPTED_NOTE_KEY, encrypted) }
                inputNote.text.clear()
                decryptedNote.text = "Note successfully encrypted and saved."
            }
        }

        //Load button function
        loadButton.setOnClickListener {
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val encrypted = prefs.getString(ENCRYPTED_NOTE_KEY, null)
            if (!encrypted.isNullOrEmpty()){
                val decrypted = AES256Helper.decrypt(encrypted)
                decryptedNote.text = decrypted
            } else{
                decryptedNote.text = "No notes found."
            }
        }
    }
}