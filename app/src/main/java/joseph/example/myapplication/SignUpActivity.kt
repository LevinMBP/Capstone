package joseph.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import joseph.example.myapplication.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.singinText.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

        }

        binding.button.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()
            val confirm = binding.confirmText.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                if(password == confirm) {
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        else {
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
                }
                else {
                    Toast.makeText(this, "Password should match", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "All Fields are Required", Toast.LENGTH_SHORT).show()
            }

        }

    }
}