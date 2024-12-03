package joseph.example.myapplication.general

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import joseph.example.myapplication.R
import joseph.example.myapplication.modals.ReminderData
import joseph.example.myapplication.utils.AppPreferences
import java.util.Calendar

class ReminderActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var btnSave: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        btnDate = findViewById(R.id.btnDate)
        btnTime = findViewById(R.id.btnTime)
        btnSave = findViewById(R.id.btnSave)

        btnDate.setOnClickListener { showDatePicker() }
        btnTime.setOnClickListener { showTimePicker() }
        btnSave.setOnClickListener { saveReminder() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            btnDate.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            btnTime.text = selectedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun saveReminder() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()

        if (title.isBlank() || selectedDate.isBlank() || selectedTime.isBlank()) {
            Toast.makeText(this, "Please fill in all required fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val reminder = ReminderData(
            title = title,
            description = description,
            date = selectedDate,
            time = selectedTime
        )
        Toast.makeText(this, "Reminder saved successfully!", Toast.LENGTH_SHORT).show()
        finish()

//        val studentId = AppPreferences.studentID // Get student ID from preferences
//        val databaseReference = FirebaseDatabase.getInstance().reference
//            .child(studentId).child("reminders")
//
//        val newReminderId = databaseReference.push().key ?: return
//
//        databaseReference.child(newReminderId).setValue(reminder).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(this, "Reminder saved successfully!", Toast.LENGTH_SHORT).show()
//                finish() // Close the activity
//            } else {
//                Toast.makeText(this, "Reminder saved successfully!", Toast.LENGTH_SHORT).show()
//                finish()
//                //Toast.makeText(this, "Failed to save reminder!", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}