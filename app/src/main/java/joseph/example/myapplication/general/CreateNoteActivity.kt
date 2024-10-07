package joseph.example.myapplication.general

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import joseph.example.myapplication.R
import joseph.example.myapplication.modals.FileUploadData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notes)

        val saveButton = findViewById<Button>(R.id.buttonSaveNote)

        saveButton.setOnClickListener {
            val title = findViewById<EditText>(R.id.editTextNoteTitle).text.toString()
            val subject = findViewById<EditText>(R.id.editTextSubjectName).text.toString()
            val unit = findViewById<EditText>(R.id.editTextUnitNumber).text.toString()

            if (title.isNotEmpty() && subject.isNotEmpty() && unit.isNotEmpty()) {
                val newNote = FileUploadData(
                    fileName = title,
                    subjectName = subject,
                    unitNumber = unit,
                    studentName = "Your Name", // Replace with the user's name if applicable
                    dateOfPublishing = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(
                        Date()
                    ),
                    fileURL = "" // Optional, if there's a file URL
                )

                val intent = Intent()
                intent.putExtra("NEW_NOTE", newNote)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
