package joseph.example.myapplication.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import joseph.example.myapplication.R
import joseph.example.myapplication.SignInActivity
import joseph.example.myapplication.general.HomeActivity

class GettingStartedActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_getting_started)
        var btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            // Set flags to clear the back stack and prevent going back to GetStartedActivity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()

        // Check if the user is already logged in
        if (firebaseAuth.currentUser != null) {
            // User is already signed in, redirect to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Close the GettingStartedActivity
        }
    }
}