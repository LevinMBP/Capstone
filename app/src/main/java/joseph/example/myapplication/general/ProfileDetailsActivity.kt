package joseph.example.myapplication.general

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import joseph.example.myapplication.R
import joseph.example.myapplication.databinding.ActivityProfileDetailsBinding


class ProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val name = intent.getStringExtra("name")
        val studentID = intent.getStringExtra("role")

        // Set data to views
        binding.profileName.text = name
        binding.profileStudentID.text = studentID

    }
}