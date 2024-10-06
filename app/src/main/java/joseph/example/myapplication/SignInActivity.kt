package joseph.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import joseph.example.myapplication.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

        binding.button.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        val exception = it.exception
                        if (exception != null) {
                            val errMessage = exception.localizedMessage ?: "An unknown error occurred."
                            Toast.makeText(this, errMessage, Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this, "Unknown error occurred.", Toast.LENGTH_SHORT)                                    .show()
                        }
                    }
                }

            } else {
                Toast.makeText(this, "All Fields are Required", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
}